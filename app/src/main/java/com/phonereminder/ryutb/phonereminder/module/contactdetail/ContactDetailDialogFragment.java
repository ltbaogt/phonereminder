package com.phonereminder.ryutb.phonereminder.module.contactdetail;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.base.BaseDialogFragment;
import com.phonereminder.ryutb.phonereminder.control.AppEditText;
import com.phonereminder.ryutb.phonereminder.control.AppTextView;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;
import com.phonereminder.ryutb.phonereminder.util.Constants;
import com.phonereminder.ryutb.phonereminder.util.SharePrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ryutb on 11/01/2018.
 */

public class ContactDetailDialogFragment extends BaseDialogFragment implements ContactDetailMvp.View {

    public interface OnDialogFragmentClickListener {
        void onDismiss();

        void onClickAdd();

        void onClickCancel();
    }

    Unbinder mUnbinder;
    @BindView(R.id.tvName)
    AppTextView tvName;
    @BindView(R.id.tvPhone)
    AppTextView tvPhone;
    @BindView(R.id.etNote)
    AppEditText etNote;

    @OnClick(R.id.btnPos)
    void createReminder() {
        ReminderItem item = new ReminderItem(mContactName, mContactNumber, etNote.getText().toString().trim());
        SharePrefUtil.putReminderItem(getContext(), item);
        if (mlistener != null) mlistener.onClickAdd();
    }

    private Uri mContactUri;
    private String mContactName;
    private String mContactNumber;
    private OnDialogFragmentClickListener mlistener;
    private ContactDetailMvp.Presenter mPresenter;

    public static ContactDetailDialogFragment getInstance(Uri contactUri) {
        ContactDetailDialogFragment fragment = new ContactDetailDialogFragment();
        Bundle arg = fragment.getArguments();
        if (arg == null) arg = new Bundle();
        arg.putString(Constants.CONTACT_URI, contactUri.toString());
        fragment.setArguments(arg);
        return fragment;
    }

    public void setOnDialogFragmentClickListener(OnDialogFragmentClickListener listener) {
        this.mlistener = listener;
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
        setupMvp();
        mPresenter.renderName();
        mPresenter.renderPhone();
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.destroy();
        mPresenter = null;
    }

    public void updateContactUri(Uri cUri) {
        Bundle arg = getArguments();
        if (arg == null) arg = new Bundle();
        arg.putString(Constants.CONTACT_URI, cUri.toString());
        setArguments(arg);
    }

    @Override
    public void setupMvp() {
        mPresenter = new ContactDetailPresenter(this, new ContactDetailModel());
    }

    @Override
    public void displayName() {

        // querying contact data store
        Cursor cursor = getActivity().getContentResolver().query(mContactUri, null, null, null, null);

        if (cursor.moveToFirst()) {
            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            mContactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            tvName.setText(mContactName);
        }
        cursor.close();
    }

    @Override
    public void displayPhone() {
        String contactID = "";
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
            mContactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            tvPhone.setText(tvPhone.getText() + "\n" + mContactNumber);
        }
    }
}
