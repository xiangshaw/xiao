package plus.axz.comment;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import plus.axz.model.comment.pojos.Comment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author xiaoxiang
 * description
 */
@SpringBootTest(classes = CommentApplication.class)
@RunWith(SpringRunner.class)
public class TestMongoDBTime {
    @Autowired
    private MongoTemplate mongoTemplate;

    @org.junit.Test
    public void testQuery() {
        // 获取时间
        Calendar calendar = getMongoDBTime();

        Date date = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formattedDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(formattedDate.format(date));

        /*is等于 and   lt小于     limit   */
        Query query = Query.query(Criteria
                        .where("entryId")
                        .is("1536989487131893762")
                        .and("createdTime")
                        .lt(10))
                .limit(5)
                .with(Sort.by(Sort.Direction.DESC, "createdTime"));/*倒叙查询*/
        Pageable pageable = PageRequest.of(1, 3);
        query.with(pageable);/*分页查询*/

        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        System.out.println(comments);
    }

    private Calendar getMongoDBTime() {
        Calendar calle = Calendar.getInstance();
        calle.setTime(new Date());
        calle.add(Calendar.HOUR_OF_DAY, +8);
        return calle;
    }

    public static void main(String[] args) {
        /*Date date = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formattedDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(formattedDate.format(date));*/
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        long time = date.getTime();
        long a = time - 28800000;
        Date date1 = new Date(a);
        String format = dateFormat.format(a);
        /**
         1687373093771
         Thu Jun 22 02:44:53 CST 2023
         2023-06-22 02:44:53:771
         */
        System.out.println(a);
        System.out.println(date1);
        System.out.println(format);
    }
}
