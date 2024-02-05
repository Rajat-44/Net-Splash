package com.apps.rajat.netsplashversion2;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class Home extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Dialog myDialog;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView logo;
        CardView browse_card, setting_card, info_card, home_card, about_card;

        browse_card = (CardView) vi.findViewById(R.id.browsecard);
        setting_card = (CardView) vi.findViewById(R.id.settingcard);
        info_card = (CardView) vi.findViewById(R.id.infocard);
        home_card = (CardView) vi.findViewById(R.id.homecard);
        about_card = (CardView) vi.findViewById(R.id.aboutcard);
        logo = (ImageView) vi.findViewById(R.id.logodash);

        YoYo.with(Techniques.Pulse)
                .repeat(99)
                .playOn(logo);

        browse_card.setOnClickListener(this);
        setting_card.setOnClickListener(this);
        about_card.setOnClickListener(this);
        home_card.setOnClickListener(this);
        info_card.setOnClickListener(this);


        return vi;

    }

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

    @Override
    public void onClick(View v)  {

        Fragment cardFrag = null;
        Class fragClass;
        fragClass = null;
        switch (v.getId()){
            case R.id.browsecard:
                fragClass = Browser.class ;
                getActivity().setTitle("Browser");
            break;
            case R.id.homecard:
                fragClass = Home.class;
                getActivity().setTitle("Net Splash V2");
                break;
            case R.id.settingcard:
                fragClass = Settings.class;
                getActivity().setTitle("Settings");
                break;
            case R.id.infocard:
                fragClass = Help.class;
                getActivity().setTitle("Help");
                break;
            case R.id.aboutcard:
                ShowPopup();
                fragClass = About.class;
                getActivity().setTitle("About");
                break;
                default: fragClass = Home.class;
        }
        try{
            cardFrag =  (Fragment) fragClass.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentcontent, cardFrag).commit();

    }

    public void ShowPopup(){
        myDialog  = new Dialog(getContext());
        myDialog.setContentView(R.layout.popupabout);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
