package ayy.shopping.cashdisplay;
/*
  Created by Karl on 19 Mar 2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ayy.shopping.R;

// Instances of this class are fragments representing a single
// object in our collection.
public class CashFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.cash_fragment, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.txt1)).setText(Integer.toString(1234567890));

        return rootView;
    }
}
