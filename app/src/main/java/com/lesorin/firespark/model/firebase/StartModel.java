package com.lesorin.firespark.model.firebase;

import static com.lesorin.firespark.model.firebase.ModelConstants.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lesorin.firespark.presenter.StartContract;
import java.util.Arrays;
import java.util.HashMap;

public class StartModel implements StartContract.Model
{
    private StartContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;
    private FirebaseFirestore _firestore;

    public StartModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
    }

    public void setPresenter(StartContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void requestSignUp(String firstLastName, String username, String email, String password)
    {
        if(!email.isEmpty() && !password.isEmpty())
        {
            _firestore.collection(COLLECTION_USERS).whereEqualTo(USER_USERNAMEINSENSITIVE, username.toLowerCase()).
                    get().addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    if(task.getResult().size() == 0)
                    {
                        _firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task2 ->
                        {
                            if(task2.isSuccessful())
                            {
                                createUserInFirestore(firstLastName, username);
                                sendVerificationEmail();
                            }
                            else
                            {
                                handleCreateUserException(task2.getException());
                            }
                        });
                    }
                    else
                    {
                        _presenter.responseSignUpUsernameNotAvailable();
                    }
                }
                else
                {
                    _presenter.responseSignUpUnknownError();
                }
            });
        }
        else
        {
            _presenter.responseSignUpEmptyEmailOrPassword();
        }
    }

    private void createUserInFirestore(String firstLastName, String username)
    {
        FirebaseUser user = _firebaseAuth.getCurrentUser();

        if(user != null)
        {
            HashMap<String, Object> userMap = new HashMap<>();

            userMap.put(USER_FIRSTLASTNAME, firstLastName);
            userMap.put(USER_USERNAME, username);
            userMap.put(USER_USERNAMEINSENSITIVE, username.toLowerCase());
            userMap.put(USER_FOLLOWERS, Arrays.asList());
            userMap.put(USER_FOLLOWING, Arrays.asList());

            _firestore.collection(COLLECTION_USERS).document(user.getUid()).set(userMap);
        }
    }

    private void setUserName(String name)
    {
        if(name != null && !name.isEmpty())
        {
            FirebaseUser user = _firebaseAuth.getCurrentUser();

            if(user != null)
            {
                UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();

                upcrb.setDisplayName(name);

                UserProfileChangeRequest upcr = upcrb.build();

                user.updateProfile(upcr);
            }
        }
    }

    private void handleCreateUserException(Exception exception)
    {
        try
        {
            throw exception;
        }
        catch(FirebaseAuthUserCollisionException e)
        {
            _presenter.responseSignUpEmailNotAvailable();
        }
        catch (FirebaseAuthWeakPasswordException wpe)
        {
            _presenter.responseSignUpWeakPassword();
        }
        catch(FirebaseAuthInvalidCredentialsException ice)
        {
            _presenter.responseSignUpInvalidEmail();
        }
        catch(Exception e)
        {
            _presenter.responseSignUpUnknownError();
        }
    }

    @Override
    public void requestLogIn(String email, String password)
    {
        if(!email.isEmpty() && !password.isEmpty())
        {
            _firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    FirebaseUser user = _firebaseAuth.getCurrentUser();

                    if(user != null)
                    {
                        if(user.isEmailVerified())
                        {
                            _presenter.responseLogInSuccess();
                        }
                        else
                        {
                            _presenter.responseLogInEmailNotVerified();
                        }
                    }
                }
                else
                {
                    _presenter.responseLogInFailure();
                }
            });
        }
        else
        {
            _presenter.responseLogInFailure();
        }
    }

    @Override
    public boolean isUserSignedIn()
    {
        return _firebaseAuth.getCurrentUser() != null && _firebaseAuth.getCurrentUser().isEmailVerified();
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = _firebaseAuth.getCurrentUser();

        if(user != null)
        {
            user.sendEmailVerification().addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    _presenter.responseSignUpVerificationEmailSent();
                }
                else
                {
                    _presenter.responseSignUpVerificationEmailNotSent();
                }
            });
        }
        else
        {
            _presenter.responseSignUpVerificationEmailNotSent();
        }
    }
}