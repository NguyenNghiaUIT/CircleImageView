package com.example.nguyennghia.circleimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.nguyennghia.circleimageview.adapter.AvatarBoxAdapter;
import com.example.nguyennghia.circleimageview.model.AvatarBox;
import com.example.nguyennghia.circleimageview.model.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewCircleImageTestActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView lvAuthor;
    private AvatarBoxAdapter mAdapter;
    private List<AvatarBox> mAvartarBox;
    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_circle_image_test);
        lvAuthor = (ListView) findViewById(R.id.lv_author);

        mAvartarBox = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Picture p = new Picture();
            int value = mRandom.nextInt(10);
            if (value == 0) {
                p.setUrl("https://freeiconshop.com/files/edd/person-flat.png");
            } else if (value == 1) {
                p.setUrl("https://freeiconshop.com/files/edd/person-girl-flat.png",
                        "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR2ge23vPK5QGirH4rAZS0VPZ52f3ih-dphgsOueNkjHMWPSfE7xQ");

            } else if (value == 2) {
                p.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT005bJfnx1Desoy6A9cXibA5RoG7Wn0wrnfSMesTCHRW7RGsLu",
                        "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTmJq3zb9ulmd5ktqsHB8qnaB1fZfdhEUvRFB1p2yPt58Zw6gUC",
                        "http://i.istockimg.com/file_thumbview_approve/32997494/3/stock-illustration-32997494-cartoon-girl-icon-avatar-portrait-illustration-series-5.jpg");
            } else if (value == 3) {
                p.setUrl("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTcYq_BnC2jpFij7NgA8WLPgfTbYFNEIYSVe8AYn2PPNkXl7cbF",
                        "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRPbbzWbuts7gvMtr7hhqO0CdaxI2aayjgA0iPcogxES1Qq2IUtJQ",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTiHklBMs7YOugHKtq-eX7eDhT9DS6uZ4Vq0kAhP9h_hjrWzvIC",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKdb6qICZJPNF3URb-xnG3S5GEhBT0LqYwvi14MEH9izEplMnK");
            } else {
                List<String> urls = new ArrayList<>();
                for (int index = 0; index < value; index++) {
                    urls.add("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSddl938GzsqMIBVkMWr5jcCX8pR4HODD-TroP-vntgzhL0UzJw");
                }
                p.setUrl(urls);

//                pMore.setUrl("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSddl938GzsqMIBVkMWr5jcCX8pR4HODD-TroP-vntgzhL0UzJw",
//                        "http://www.bmj.com/company/wp-content/uploads/2014/08/BMJ-Outcomes-image.png",
//                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIcdgqLk9onTbdTU7tmIzhMf-YHv9n95rFnr0zjtI916CBvy0a",
//                        "https://pbs.twimg.com/profile_images/667746954747600896/h25k_tv_.png",
//                        "https://pbs.twimg.com/profile_images/487242217887502337/qOMRQbPk_400x400.jpeg");
//                mAvartarBox.add(new AvatarBox("AvartarBox " + i, pMore));
            }
            mAvartarBox.add(new AvatarBox("Item " + i, p));

        }

        mAdapter = new AvatarBoxAdapter(this, mAvartarBox);
        lvAuthor.setAdapter(mAdapter);


    }
}
