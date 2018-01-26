package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.clinic.romeo.dental.R;
import com.clinic.romeo.dental.activity.activity.BracesActivity;
import com.clinic.romeo.dental.activity.activity.BrushActivity;
import com.clinic.romeo.dental.activity.activity.CoverActivity;
import com.clinic.romeo.dental.activity.activity.HealRootActivity;
import com.clinic.romeo.dental.activity.activity.LooseActivity;
import com.clinic.romeo.dental.activity.activity.PullActivity;
import com.clinic.romeo.dental.activity.activity.WisdomActivity;



@SuppressWarnings("unused")
public class MenuclientFragment extends Fragment {

    /********************
     * Variable Zone
     ********************/

 ImageButton braces_teeth_imgButton,brush_teeth_imgButton,cover_teeth_imgButton
            ,heal_root_teeth_imgButton,loose_teeth_imgButton,pull_teeth_imgButton
            ,wisdom_teeth_imgButton;
    /********************
     * Function Zone
     ********************/
    public MenuclientFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MenuclientFragment newInstance() {
        MenuclientFragment fragment = new MenuclientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menuclient, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        braces_teeth_imgButton =(ImageButton) rootView.findViewById(R.id.braces_teeth_imgButton);
        brush_teeth_imgButton =(ImageButton) rootView.findViewById(R.id.brush_teeth_imgButton);
        cover_teeth_imgButton  =(ImageButton) rootView.findViewById(R.id.cover_teeth_imgButton);
        heal_root_teeth_imgButton =(ImageButton) rootView.findViewById(R.id.heal_root_teeth_imgButton);
        loose_teeth_imgButton =(ImageButton) rootView.findViewById(R.id.loose_teeth_imgButton);
        pull_teeth_imgButton =(ImageButton) rootView.findViewById(R.id.pull_teeth_imgButton);
        wisdom_teeth_imgButton =(ImageButton) rootView.findViewById(R.id.wisdom_teeth_imgButton);

        braces_teeth_imgButton.setOnClickListener(braces_listener);
        brush_teeth_imgButton.setOnClickListener(brush_listener);
        cover_teeth_imgButton.setOnClickListener(cover_listener);
        heal_root_teeth_imgButton.setOnClickListener(heal_listener);
        loose_teeth_imgButton.setOnClickListener(loose_listener);
        pull_teeth_imgButton.setOnClickListener(pull_listener);
        wisdom_teeth_imgButton.setOnClickListener(wisdom_listener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }
    /********************
     * Listener Zone
     ********************/
    View.OnClickListener braces_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), BracesActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener brush_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), BrushActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener cover_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), CoverActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener heal_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), HealRootActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener loose_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), LooseActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener pull_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PullActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener wisdom_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), WisdomActivity.class);
            startActivity(intent);
        }
    };
    /********************
     * Inner Zone
     ********************/
}
