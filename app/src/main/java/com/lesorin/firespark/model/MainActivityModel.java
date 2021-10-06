package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.presenter.Spark;
import java.util.ArrayList;
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
        _firestore.collection(SPARKS_COLLECTION).whereArrayContains("subscribers", _firebaseAuth.getCurrentUser().getUid()).
                whereEqualTo("isdeleted", false).get().addOnCompleteListener(task ->
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
    public void sendSpark(Spark spark)
    {
        HashMap<String, Object> toInsert = sparkToInsertMap(spark);

        //todo need to get the followers of the current user and add them to the spark's subscribers list

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

    private HashMap<String, Object> sparkToInsertMap(Spark spark)
    {
        HashMap<String, Object> ret = new HashMap<>();
        String currentUserId = _firebaseAuth.getCurrentUser().getUid();

        spark.addSubscriber(currentUserId);

        //todo fix
        ret.put("body", spark.getBody());
        ret.put("created", FieldValue.serverTimestamp());
        ret.put("owner", currentUserId);
        ret.put("ownername", _firebaseAuth.getCurrentUser().getDisplayName());
        ret.put("likes", spark.getLikes());
        ret.put("subscribers", spark.getSubscribers());
        ret.put("isdeleted", false);

        return ret;
    }

    private Spark createSparkFromDocumentSnapshot(QueryDocumentSnapshot document)
    {
        Spark spark = document.toObject(Spark.class);

        spark.setId(document.getId());
        spark.setOwnedByCurrentUser(spark.getOwnerId().equals(_firebaseAuth.getUid()));

        return spark;
    }
}