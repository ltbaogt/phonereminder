package com.phonereminder.ryutb.phonereminder;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonereminder.ryutb.phonereminder.base.BaseDialogFragment;
import com.phonereminder.ryutb.phonereminder.control.AppEditText;
import com.phonereminder.ryutb.phonereminder.control.AppTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ryutb on 11/01/2018.
 */

public class AddingContantDialogFragment extends BaseDialogFragment {

    Unbinder mUnbinder;
    @BindView(R.id.tvName)
    AppTextView tvName;
    @BindView(R.id.tvPhone)
    AppTextView tvPhone;
    @BindView(R.id.etNote)
    AppEditText etNote;

    Uri mContactUri;

    public static AddingContantDialogFragment getInstance(Uri contactUri) {
        AddingContantDialogFragment fragment = new AddingContantDialogFragment();
        Bundle arg = fragment.getArguments();
        if (arg == null) arg = new Bundle();
        arg.putString(Constants.CONTACT_URI, contactUri.toString());
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.add_contact_fragment_layout, container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        mContactUri = Uri.parse(getArguments().getString(Constants.CONTACT_URI));
        retrieveContactName();
        retrieveContactNumber();
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getActivity().getContentResolver().query(mContactUri, null, null, null, null);

        if (cursor.moveToFirst()) {
            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            tvName.setText(contactName);
        }
        cursor.close();
    }

    private void retrieveContactNumber() {

        String contactID = "";
        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getActivity().getContentResolver().query(mContactUri,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            tvPhone.setText(tvPhone.getText() + "\n" + contactNumber);
        }

        cursorPhone.close();
    }
}
