package ayy.shopping.cashdisplay;
/*
  Created by Karl on 19 Mar 2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ayy.shopping.R;

// Instances of this class are fragments representing a single
// object in our collection.
public class CashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.cash_fragment, container, false);
        Bundle args = getArguments();

        ((ImageView) rootView.findViewById(R.id.cashpic)).setImageResource(args.getInt("o"));

        return rootView;
    }
}
