package com.brotherd.poemtrip.model;

/**
 * Created by dumingwei on 2017/5/20.
 */
public class PoetModel {


    /**
     * description :
     富嘉谟（?～706）字号不详。雍州武功（陕西武功）人。唐代散文家。举进士。武则天长安中累授晋阳尉，时吴少微也在晋阳，魏郡谷倚为太原主簿，三人均以文词见长，称为“北京三杰”。又以文词崇雅黜浮，浑厚雄迈，而被仿效，称为“吴富体”。唐中宗时预修《三教珠英》。中宗神龙初，韦嗣立荐为左台监察御史，不久病逝。《上官昭容集》20卷唐上官婉儿撰。《新唐书&#183;艺文志》著录，佚。今《全唐诗》卷5收其诗32首，并云昭容词旨益新。当时属辞者，大抵虽浮艳，然皆有可观处。

     * imageUrl : http://img.gushiwen.org/authorImg/fujiamo.jpg
     * poetId : 207
     * poetName : 富嘉谟
     */

    private String description;
    private String imageUrl;
    private int poetId;
    private String poetName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPoetId() {
        return poetId;
    }

    public void setPoetId(int poetId) {
        this.poetId = poetId;
    }

    public String getPoetName() {
        return poetName;
    }

    public void setPoetName(String poetName) {
        this.poetName = poetName;
    }
}
