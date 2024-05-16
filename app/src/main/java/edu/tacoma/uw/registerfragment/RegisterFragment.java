package edu.tacoma.uw.registerfragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.tacoma.uw.registerfragment.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding mBinding;

    private UserViewModel mUserViewModel;
    @Override

    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        mUserViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        // Instantiate the Binding object and Inflate the layout for this fragment
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }
    @Override
    public void onDestroyView() {

        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            observeResponse(response);

        });
        //Use a Lamda expression to add the OnClickListener
        mBinding.registerButton.setOnClickListener(button -> register());
    }

    public void register() {
            String email = String.valueOf(mBinding.emailEdit.getText());
            String pwd = String.valueOf(mBinding.pwdEdit.getText());
            Account account;

            try{
                account = new Account(email, pwd);

            }catch(IllegalArgumentException ie){
                Log.e(TAG, ie.getMessage());
                Toast.makeText(getContext(), ie.getMessage(), Toast.LENGTH_LONG).show();
                mBinding.textError.setText(ie.getMessage());
                return;
            }
            Log.i(TAG, email);
           mUserViewModel.addUser( account);

        }


    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("error")) {
                try {
                    String err = "Error Adding User :" + response.get("error");
                    Toast.makeText(this.getContext(),
                            "Error Adding User: " +
                                    response.get("error"), Toast.LENGTH_LONG).show();

                   mBinding.textError.setText(err);

                } catch (JSONException e) {
                    String err = "Error Adding User: " + e.getMessage();
                    Log.e(TAG, err);
                    mBinding.textError.setText(err);
                    Log.e("JSON Parse Error", e.getMessage());
                }

            } else {
                Toast.makeText(this.getContext(),"User added", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).popBackStack();
            }

        } else {
            Log.d("JSON Response", "No Response");
        }

    }






}