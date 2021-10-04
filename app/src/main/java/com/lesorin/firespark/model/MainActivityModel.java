package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lesorin.firespark.presenter.MainActivityContract;
import java.util.HashMap;

public class MainActivityModel implements MainActivityContract.Model
{
    private final String SPARKS_COLLECTION = "sparks";

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
        //todo get real data
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
    public void sendSpark(MainActivityContract.Spark spark)
    {
        HashMap<String, Object> toInsert = sparkToInsertMap(spark);

        /*_firestore.collection(SPARKS_COLLECTION).add(toInsert).addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                //todo
            }
            else
            {
                //todo
            }
        });*/
    }

    private HashMap<String, Object> sparkToInsertMap(MainActivityContract.Spark spark)
    {
        HashMap<String, Object> ret = new HashMap<>();
        String currentUserId = _firebaseAuth.getCurrentUser().getUid();

        spark._subscribers.add(currentUserId);

        ret.put("body", spark._text);
        ret.put("created", FieldValue.serverTimestamp());
        ret.put("owner", currentUserId);
        ret.put("likes", spark._likes);
        ret.put("subscribers", spark._subscribers);
        ret.put("isDeleted", false);

        return ret;
    }
}