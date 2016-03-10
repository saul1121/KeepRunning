package jobschedule.mac.android.com.keeprunning;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {


    Chronometer mChronometer;
    Button btnStartJob, btnCancelJobs;

    JobScheduler mJobScheduler;
    private static final int MYJOBID= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/


        mChronometer = (Chronometer)findViewById(R.id.chronometer);
        btnStartJob = (Button)findViewById(R.id.startjob);
        btnCancelJobs =  (Button)findViewById(R.id.canceljobs);

        mJobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        btnStartJob.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();


                ComponentName jobService = new ComponentName(getPackageName(), KeepRunningJobService.class.getName());
                JobInfo jobInfo = new JobInfo.Builder(MYJOBID, jobService).setPeriodic(10000).build();
    /*
     * setPeriodic(long intervalMillis)
     * Specify that this job should recur with the provided interval,
     * not more than once per period.
     */

                int jobId = mJobScheduler.schedule(jobInfo);
                if(mJobScheduler.schedule(jobInfo)>0){
                    Toast.makeText(MainActivity.this,
                            "Successfully scheduled job: " + jobId,
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "RESULT_FAILURE: " + jobId,
                            Toast.LENGTH_SHORT).show();
                }

            }});


        btnCancelJobs.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mChronometer.stop();

                List<JobInfo> allPendingJobs = mJobScheduler.getAllPendingJobs();
                String s = "";
                for(JobInfo j : allPendingJobs){
                    int jId = j.getId();
                    mJobScheduler.cancel(jId);
                    s += "jobScheduler.cancel(" + jId + " )";
                }
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

                //or
                //jobScheduler.cancelAll();


            }});







 /*       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
 */   }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
