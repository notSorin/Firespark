package com.lesorin.firespark.model;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lesorin.firespark.presenter.StartActivityContract;
import java.util.Arrays;
import java.util.HashMap;

public class StartActivityModel implements StartActivityContract.Model
{
    private StartActivityContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;
    private FirebaseFirestore _firestore;

    public StartActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
    }

    public void setPresenter(StartActivityContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void createUser(String firstLastName, String username, String email, String password)
    {
        if(!email.isEmpty() && !password.isEmpty())
        {
            _firestore.collection(USERS_COLLECTION).whereEqualTo(USER_USERNAMEINSENSITIVE, username.toLowerCase()).
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
                        _presenter.failedToCreateUserUsernameAlreadyExists();
                    }
                }
                else
                {
                    _presenter.failedToCreateUserUnknownError();
                }
            });
        }
        else
        {
            _presenter.failedToCreateUserEmptyEmailOrPassword();
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

            _firestore.collection(USERS_COLLECTION).document(user.getUid()).set(userMap);
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
            _presenter.failedToCreateUserEmailAlreadyExists();
        }
        catch (FirebaseAuthWeakPasswordException wpe)
        {
            _presenter.failedToCreateUserWeakPassword();
        }
        catch(FirebaseAuthInvalidCredentialsException ice)
        {
            _presenter.failedToCreateUserInvalidEmail();
        }
        catch(Exception e)
        {
            _presenter.failedToCreateUserUnknownError();
        }
    }

    @Override
    public void logUserIn(String email, String password)
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
                            _presenter.logUserInSuccess();
                        }
                        else
                        {
                            _presenter.logUserInFailureNotVerified();
                        }
                    }
                }
                else
                {
                    _presenter.logUserInFailure();
                }
            });
        }
        else
        {
            _presenter.logUserInFailure();
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
                    _presenter.createUserVerificationEmailSent();
                }
                else
                {
                    _presenter.createUserVerificationEmailNotSent();
                }
            });
        }
        else
        {
            _presenter.createUserVerificationEmailNotSent();
        }
    }
}