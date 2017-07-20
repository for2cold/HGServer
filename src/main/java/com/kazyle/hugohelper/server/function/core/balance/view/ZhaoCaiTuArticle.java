package com.kazyle.hugohelper.server.function.core.balance.view;

/**
 * <p>
 * <b>ZhaoCaiTuArticle</b> is
 * </p>
 *
 * @author HuangWeigang
 * @version $$id$$
 * @since 2017/7/20
 */
public class ZhaoCaiTuArticle {

    private Long id;

    private String ys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    @Override
    public String toString() {
        return "ZhaoCaiTuArticle{" +
                "id=" + id +
                ", ys='" + ys + '\'' +
                '}';
    }
}
