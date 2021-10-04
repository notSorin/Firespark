package com.lesorin.firespark.model;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lesorin.firespark.presenter.MainActivityContract;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivityModel implements MainActivityContract.Model
{
    private final String SPARKS_COLLECTION = "sparks";
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm";

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
                ArrayList<MainActivityContract.Spark> sparks = new ArrayList<>();

                for(QueryDocumentSnapshot document : task.getResult())
                {
                    MainActivityContract.Spark spark = createSparkFromDocumentSnapshot(document);

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
    public void sendSpark(MainActivityContract.Spark spark)
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

    private HashMap<String, Object> sparkToInsertMap(MainActivityContract.Spark spark)
    {
        HashMap<String, Object> ret = new HashMap<>();
        String currentUserId = _firebaseAuth.getCurrentUser().getUid();

        spark._subscribers.add(currentUserId);

        ret.put("body", spark._text);
        ret.put("created", FieldValue.serverTimestamp());
        ret.put("owner", currentUserId);
        ret.put("ownername", _firebaseAuth.getCurrentUser().getDisplayName());
        ret.put("likes", spark._likes);
        ret.put("subscribers", spark._subscribers);
        ret.put("isdeleted", false);

        return ret;
    }

    private MainActivityContract.Spark createSparkFromDocumentSnapshot(QueryDocumentSnapshot document)
    {
        MainActivityContract.Spark spark = document.toObject(MainActivityContract.Spark.class);
        Map<String, Object> data = document.getData();
        Timestamp timestamp = (Timestamp)data.get("created");
        Date date = timestamp.toDate();
        String formattedDate = new SimpleDateFormat(DATE_FORMAT).format(date);

        spark._id = document.getId();
        spark._ownedByCurrentUser = spark._ownerId.equals(_firebaseAuth.getUid());
        spark._created = formattedDate;

        return spark;
    }
}