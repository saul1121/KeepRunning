package jobschedule.mac.android.com.keeprunning;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by superadmin on 3/9/16.
 */
public class KeepRunningJobService extends JobService {
    private static final String TAG = "SyncService";


    @Override
    public boolean onStartJob(JobParameters params) {
        // We don't do any real 'work' in this sample app. All we'll
        // do is track which jobs have landed on our service, and
        // update the UI accordingly.
        Toast.makeText(KeepRunningJobService.this, "KeepRunningJobService.onStartJob()", Toast.LENGTH_SHORT).show();

       /*
   * True - if your service needs to process
   * the work (on a separate thread).
   * False - if there's no more work to be done for this job.
   */
        return false;

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(KeepRunningJobService.this,"KeepRunningJobService.onStopJob()", Toast.LENGTH_SHORT);

        Log.i(TAG, "on stop job: " + params.getJobId());

        return false;

    }

    MainActivity mActivity;
    private final LinkedList<JobParameters> jobParamsMap = new LinkedList<JobParameters>();

    public void setUiCallback(MainActivity activity) {
        mActivity = activity;
    }

}