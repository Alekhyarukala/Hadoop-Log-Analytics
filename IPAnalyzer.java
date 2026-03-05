import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IPAnalyzer {

    public static class IPMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

        private Text ip = new Text();
        private final static IntWritable one = new IntWritable(1);

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString();
            String[] parts = line.split(" ");

            if(parts.length > 8){
                ip.set(parts[0]);   // first column is IP address
                context.write(ip, one);
            }
        }
    }

    public static class IPReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {

            int sum = 0;

            for(IntWritable val : values){
                sum += val.get();
            }

            context.write(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "IP Analyzer");

        job.setJarByClass(IPAnalyzer.class);
        job.setMapperClass(IPMapper.class);
        job.setReducerClass(IPReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

