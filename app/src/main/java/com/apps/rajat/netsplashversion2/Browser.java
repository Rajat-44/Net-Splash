package com.apps.rajat.netsplashversion2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnShowAlertListener;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Browser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Browser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Browser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Browser() {
        // Required empty public constructor

    }
    WebView webView;
    ProgressBar progressBar;
    EditText editText;
    Button button;
    Intent goother;
    LinearLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetDialog bottomSheetDialog;
    CardView cardText;
    Boolean doubleBackToExitOnce = false;
    TextView title;
    Context context;
    Typeface netSplashFont;

    String Message;
    Dialog myDialog;

    public static Browser newInstance(String param1, String param2) {
        Browser fragment = new Browser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





        }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser, container, false);

        editText = (EditText) v.findViewById(R.id.editText);
        button = (Button) v.findViewById(R.id.button);
        cardText = (CardView) v.findViewById(R.id.cardText);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        webView = (WebView) v.findViewById(R.id.webView);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

        editText.setText("" + ".com");
        editText.setSelection(0);
        //Animaton of Button
        if (Build.VERSION.SDK_INT>20) {
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    revealeffectbutton();
                }
            }, 300);
        }else{
            button.setVisibility(View.VISIBLE);
        }
        //Animaton of Card View
        if (Build.VERSION.SDK_INT>20) {
            cardText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    revealeffectcard();
                }
            }, 300);
        }else{
            cardText.setVisibility(View.VISIBLE);
        }
        //Animaton of Webview
        if (Build.VERSION.SDK_INT>20) {
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    revealeffectwebview();
                }
            },300);
        }else{
            webView.setVisibility(View.VISIBLE);
        }

        YoYo.with(Techniques.Tada)
                .duration(1000)
                .delay(500)
                .repeatMode(0)
                .playOn(button);




        //Major Functioning of WebView
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);

        } else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);


            webView.setWebViewClient(new ourViewClient());
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    progressBar.setProgress(progress);
                    if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {

                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    if (progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                }
            });
        }

        webView.loadUrl("https://www.google.com");
     /*   webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ");
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getContext(), "Downloading File", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();

            }
        });*/


        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });
        //Go Button On click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().toString().trim().isEmpty()) {
                    Alerter.create(getActivity())
                            .setIcon(R.mipmap.error)
                            .setBackgroundColorRes(R.color.alert_default_error_background)
                            .setProgressColorRes(R.color.green)
                            .enableProgress(true)
                            .setDuration(2000)
                            .enableSwipeToDismiss()
                            .setText("Please enter a URL.")
                            .setOnShowListener(new OnShowAlertListener() {
                                @Override
                                public void onShow() {
                                    editText.setText("" + ".com");
                                    editText.setSelection(0);

                                }
                            })
                            .show();

                    return;
                }
                if (editText.getText().toString().equals(".com")){

                    return;
                }
                if (editText.getText().toString().endsWith(".com")
                        || editText.getText().toString().endsWith(".co.in")
                        || editText.getText().toString().endsWith(".org")
                        || editText.getText().toString().endsWith(".in")
                         || editText.getText().toString().startsWith("117.")) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    webView.loadUrl("https://" + editText.getText().toString());
                    editText.setText("" + ".com");
                    editText.setSelection(0);
                } else {
                    Alerter.create(getActivity())
                            .setIcon(R.mipmap.browserearthonly)
                            .setTitle("Not a valid URL")
                            .setText("Please enter a valid url with .com in the end.")
                            .setBackgroundColorRes(R.color.alerter_default_success_background)
                            .setDuration(2000)
                            .setProgressColorRes(R.color.deeppurple)
                            .enableProgress(true)
                            .enableSwipeToDismiss()
                            .show();

                   /* Snackbar.make(getView().findViewById(R.id.snack), "Please enter a valid url with .com in the end.", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();*/

                }

            }

        });



        return v;

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.browsermenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_back:
                if (webView.canGoBack()){
                    webView.goBack();
                }


                return true;

            case R.id.item_forward:
                if (webView.canGoForward()){
                    webView.goForward();
                }
                return true;

            case R.id.item_home:
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                webView.loadUrl("https://www.google.co.in/");
                editText.setText("");

                return true;
            case R.id.item_reload:
                webView.reload();
                Toast.makeText(getContext(), "Reloading this page.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_stopLoading:
                webView.stopLoading();
                Toast.makeText(getContext(), "Stopped.", Toast.LENGTH_SHORT).show();
                return true;


            default:
        }

        return super.onOptionsItemSelected(item);
    }


    //Saving state of WebView


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
      //  super.onViewCreated(view, savedInstanceState);



    }

            //Loading URLs
            class ourViewClient extends WebViewClient {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    CookieManager.getInstance().setAcceptCookie(true);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            }


    void revealeffectbutton(){
        if (Build.VERSION.SDK_INT>20){
            int cx = button.getMeasuredWidth()/2;
            int cy = button.getMeasuredHeight()/2;
            int radius = Math.max(button.getWidth(),button.getHeight());
            android.animation.Animator a = ViewAnimationUtils.createCircularReveal(button,cx,cy,0,radius);
            a.setDuration(1000);
            button.setVisibility(View.VISIBLE);
            a.start();

        }
    }
    void revealeffectcard(){
        if (Build.VERSION.SDK_INT>20){
            int cx = cardText.getMeasuredWidth()/2;
            int cy = cardText.getMeasuredHeight()/2;
            int radius = Math.max(cardText.getWidth(),cardText.getHeight());
            android.animation.Animator a = ViewAnimationUtils.createCircularReveal(cardText,cx,cy,0,radius);
            a.setDuration(1000);
            cardText.setVisibility(View.VISIBLE);
            a.start();

        }
    }
    void revealeffectwebview(){
        if (Build.VERSION.SDK_INT>20){
            int cx = webView.getMeasuredWidth()/2;
            int cy = webView.getMeasuredHeight()/2;
            int radius = Math.max(webView.getWidth(),webView.getHeight());
            android.animation.Animator a = ViewAnimationUtils.createCircularReveal(webView,cx,cy,0,radius);
            a.setDuration(1000);
            webView.setVisibility(View.VISIBLE);
            a.start();

        }
    }

}