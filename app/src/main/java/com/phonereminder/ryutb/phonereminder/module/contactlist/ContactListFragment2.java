package com.phonereminder.ryutb.phonereminder.module.contactlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.base.BaseFragment;
import com.phonereminder.ryutb.phonereminder.module.contactdetail.ContactDetailDialogFragment;
import com.phonereminder.ryutb.phonereminder.module.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ryutb on 11/01/2018.
 */

public class ContactListFragment2 extends BaseFragment implements ContactDetailDialogFragment.OnDialogFragmentClickListener {


    private static final int RESULT_PICK_CONTACT = 999;
    private ContactDetailDialogFragment mContactDetailFragment;
    @BindView(R.id.btnPickContact)
    Button btnPickContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.contact_fragment_layout2, container, false);
        ButterKnife.bind(this, fragmentView);

        btnPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
            }
        });
        return fragmentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     * @param data
     */
    private void contactPicked(Intent data) {
        try {
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            if (mContactDetailFragment != null && mContactDetailFragment.getDialog() != null && mContactDetailFragment.getDialog().isShowing())
                return;
            mContactDetailFragment = ContactDetailDialogFragment.getInstance(uri);
            mContactDetailFragment.setOnDialogFragmentClickListener(this);
            mContactDetailFragment.show(getFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void onClickAdd() {
        ((MainActivity)getActivity()).updateReminderList();
    }

    @Override
    public void onClickCancel() {

    }
}
