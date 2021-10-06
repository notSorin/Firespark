package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivityModel implements MainActivityContract.Model
{
    private final String SPARKS_COLLECTION = "sparks";
    private final String USERS_COLLECTION = "users";

    private MainActivityContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;
    private FirebaseFirestore _firestore;

    public MainActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
    }

    public void setPresenter(MainActivityContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void logUserOut()
    {
        _firebaseAuth.signOut();
    }

    @Override
    public void requestProfileData()
    {
        //todo get real data
    }

    @Override
    public void requestHomeData()
    {
        //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
        _firestore.collection(SPARKS_COLLECTION).whereArrayContains("subscribers", _firebaseAuth.getCurrentUser().getUid()).
                whereEqualTo("isdeleted", false).orderBy("created", Query.Direction.DESCENDING).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                ArrayList<Spark> sparks = new ArrayList<>();

                for(QueryDocumentSnapshot document : task.getResult())
                {
                    Spark spark = createSparkFromDocumentSnapshot(document);

                    sparks.add(spark);
                }

                _presenter.homeDataAcquired(sparks);
            }
        });
    }

    @Override
    public void requestPopularData()
    {
        //todo get real data
    }

    @Override
    public String getUserName()
    {
        return _firebaseAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public void sendSpark(String sparkBody)
    {
        String userId = _firebaseAuth.getCurrentUser().getUid();

        _firestore.collection(USERS_COLLECTION).document(userId).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                DocumentSnapshot ds = task.getResult();
                User user = createUserFromDocumentSnapshot(ds);
                HashMap<String, Object> toInsert = createSparkMapForInserting(sparkBody, user);

                _firestore.collection(SPARKS_COLLECTION).add(toInsert).addOnCompleteListener(task2 ->
                {
                    if(task2.isSuccessful())
                    {
                        _firestore.collection(SPARKS_COLLECTION).document(task2.getResult().getId()).
                                get().addOnCompleteListener(task3 ->
                        {
                            if(task3.isSuccessful())
                            {
                                Spark spark = createSparkFromDocumentSnapshot(task3.getResult());

                                _presenter.sendSparkResult(spark);
                            }
                            else
                            {
                                _presenter.sendSparkResult(null);
                            }
                        });
                    }
                    else
                    {
                        _presenter.sendSparkResult(null);
                    }
                });
            }
            else
            {
                _presenter.sendSparkResult(null);
            }
        });

    }

    @Override
    public void deleteSpark(Spark spark)
    {
        if(spark.isOwnedByCurrentUser())
        {
            HashMap<String, Object> updateFields = new HashMap<>();

            updateFields.put("isdeleted", true);

            _firestore.collection(SPARKS_COLLECTION).document(spark.getId()).update(updateFields).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    _presenter.deleteSparkSuccess(spark);
                }
                else
                {
                    _presenter.deleteSparkFailure();
                }
            });
        }
        else
        {
            _presenter.deleteSparkFailure();
        }
    }

    private HashMap<String, Object> createSparkMapForInserting(String sparkBody, User user)
    {
        HashMap<String, Object> ret = new HashMap<>();

        ret.put("ownerid", _firebaseAuth.getCurrentUser().getUid());
        ret.put("ownerfirstlastname", user.getFirstlastname());
        ret.put("ownerusername", user.getUsername());
        ret.put("body", sparkBody);
        ret.put("created", FieldValue.serverTimestamp());
        ret.put("isdeleted", false);
        ret.put("likes", Arrays.asList());

        ArrayList<String> subscribers = user.getFollowers();

        //Need to add the current user to the subscribers list so they can too be able to view
        //their own sparks on the home feed.
        subscribers.add(_firebaseAuth.getCurrentUser().getUid());

        ret.put("subscribers", subscribers);

        return ret;
    }

    private Spark createSparkFromDocumentSnapshot(DocumentSnapshot document)
    {
        Spark spark = document.toObject(Spark.class);

        spark.setId(document.getId());
        spark.setOwnedByCurrentUser(spark.getOwnerId().equals(_firebaseAuth.getUid()));

        return spark;
    }

    private User createUserFromDocumentSnapshot(DocumentSnapshot document)
    {
        User user = document.toObject(User.class);

        user.setId(document.getId());

        return user;
    }
}