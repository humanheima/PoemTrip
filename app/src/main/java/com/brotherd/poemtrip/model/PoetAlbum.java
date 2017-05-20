package com.brotherd.poemtrip.model;

import java.util.List;

/**
 * Created by dumingwei on 2017/5/20.
 */
public class PoetAlbum {

    private PoetBean poet;
    private List<PoemListBean> poemList;

    public PoetBean getPoet() {
        return poet;
    }

    public void setPoet(PoetBean poet) {
        this.poet = poet;
    }

    public List<PoemListBean> getPoemList() {
        return poemList;
    }

    public void setPoemList(List<PoemListBean> poemList) {
        this.poemList = poemList;
    }

    public static class PoetBean {
        /**
         * description :
         丘为，苏州嘉兴人。事继母孝，常有灵芝生堂下。累官太子右庶子。致仕，给俸禄之半以终身。年八十馀，母尚无恙。及居忧，观察使韩滉以致仕官给禄，所以惠养老臣，不可在丧而异，惟罢春秋羊酒。卒年九十六。与刘长卿善，其赴上都也，长卿有诗送之，亦与王维为友。诗十三首。

         * imageUrl : http://img.gushiwen.org/authorImg/qiuwei.jpg
         * poetId : 333
         * poetName : 丘为
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

    public static class PoemListBean {
        /**
         * age : 唐代
         * content : 绝顶一茅茨，直上三十里。<br />扣关无僮仆，窥室唯案几。<br />若非巾柴车，应是钓秋水。<br />差池不相见，黾勉空仰止。<br />草色新雨中，松声晚窗里。<br />及兹契幽绝，自足荡心耳。<br />虽无宾主意，颇得清净理。<br />兴尽方下山，何必待之子。
         * poemId : 5669
         * poet : 丘为
         * poetId : 333
         * theme :
         * title : 寻西山隐者不遇 / 山行寻隐者不遇
         * translation : <p><strong>译文</strong><br />高高的山顶上有一座茅屋，从山下走上去足有三十里。<br />轻扣柴门竟无童仆回问声，窥看室内只有桌案和茶几。<br />主人不是驾着巾柴车外出，一定是到秋水碧潭去钓鱼。<br />错过了时机不能与他见面，空负了殷勤仰慕一片心意。<br />新雨中草色多么青翠葱绿，晚风将松涛声送进窗户里。<br />这清幽境地很合我的雅兴，足可以把身心和耳目荡涤。<br />我虽然还没有和主人交谈，却已经领悟到清净的道理。<br />玩到兴尽就满意地下山去，何必非要和这位隐者相聚。</p>
         */

        private String age;
        private String content;
        private int poemId;
        private String poet;
        private int poetId;
        private String theme;
        private String title;
        private String translation;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPoemId() {
            return poemId;
        }

        public void setPoemId(int poemId) {
            this.poemId = poemId;
        }

        public String getPoet() {
            return poet;
        }

        public void setPoet(String poet) {
            this.poet = poet;
        }

        public int getPoetId() {
            return poetId;
        }

        public void setPoetId(int poetId) {
            this.poetId = poetId;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }
}
