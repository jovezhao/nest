package models;

import com.ywkj.nest.ddd.EntityObject;

/**
 * Created by Jove on 2016/9/22.
 */
public class Product extends EntityObject {
    public String title;
    public String pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
