package sing.okhttp.okhttputils.callback;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import sing.okhttp.R;
import sing.okhttp.okhttputils.request.BaseRequest;

public class StringCallback extends AbsCallback<String> {

    private MaterialDialog dialog;
    private Context context;
    /** true显示等待框 */
    private boolean isShow;

    public StringCallback(Context context,boolean isShow) {
        this.context = context;
        this.isShow = isShow;
    }

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }

    @Override
    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
        super.onError(isFromCache, call, response, e);
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
            Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);

        if (isShow){
            MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false);

            dialog = builder.build();
            dialog.show();
        }
    }
}
