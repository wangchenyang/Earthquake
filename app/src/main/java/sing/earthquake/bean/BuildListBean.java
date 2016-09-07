package sing.earthquake.bean;

import java.io.Serializable;
import java.util.List;

import sing.earthquake.util.CommonUtil;

public class BuildListBean implements Serializable {

    /**
     * dataRows : [{"id":1154573,"jzmc":"力鸿花园3号楼","address":"朝阳区左家庄中街6号院","postcode":"100028","ssjd":"左家庄街道","ssshequ":"左北里社区","ssxiaoqu":"力鸿花园","louzuobianhao":"3号楼","jzwgd":"70","dsjzcs":"24","dxjzcs":"2","jsdw":"北京民福房地产开发公司","jgsj":"1998-03-01","sjdw":"第二炮兵设计院","sgdw":"城建四公司","jldw":"","jzmj":"26089","jzjcxs":"箱型基础,","peopleCount":"484","tuzhi":"有","yt":"住宅,","wenwudanwei":"否","kz":"八度设防","fldj":"丙类","guifan":"89规范","jglx":"钢混,","ldlx":"现浇板平屋面","cdlx":"Ⅲ类","zzwxw":"空调室外机,","sgzl":"基本齐全","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"zj03","lon":116.446533,"lat":39.963945,"shuoming":"","qtcl":"多种材料混合","ywql":"有圈梁","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471244094000},{"id":1154572,"jzmc":"周立强","address":"朝阳区王四营乡小海子22户","postcode":"100124","ssjd":"王四营地区","ssshequ":"观音堂村","ssxiaoqu":"朝阳区王四营乡小海子22户","louzuobianhao":"","jzwgd":"3","dsjzcs":"1","dxjzcs":"","jsdw":"","jgsj":"2000-06-01","sjdw":"","sgdw":"","jldw":"","jzmj":"320","jzjcxs":"独立基础,","peopleCount":"5","tuzhi":"无","yt":"住宅,","wenwudanwei":"否","kz":"不设防","fldj":"","guifan":"","jglx":"砖混,","ldlx":"非现浇板坡屋面","cdlx":"","zzwxw":"空调室外机,","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"否","uname":"xiaohaizi","lon":116.522309,"lat":39.893236,"shuoming":"","qtcl":"砖墙","ywql":"有圈梁","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471244016000},{"id":1154571,"jzmc":"西坝河西里社区9号楼","address":"朝阳区西坝河西里社区9号楼","postcode":"100028","ssjd":"香河园街道","ssshequ":"西坝河西里社区","ssxiaoqu":"西坝河西里社区","louzuobianhao":"","jzwgd":"43","dsjzcs":"12","dxjzcs":"2","jsdw":"","jgsj":"1985-08-01","sjdw":"","sgdw":"","jldw":"","jzmj":"8220","jzjcxs":"","peopleCount":"144","tuzhi":"","yt":"住宅,","wenwudanwei":"否","kz":"","fldj":"","guifan":"","jglx":"钢混,","ldlx":"","cdlx":"","zzwxw":"","sgzl":"基本齐全","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"xh05","lon":116.44347,"lat":39.972188,"shuoming":"","qtcl":"","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471244014000},{"id":1154570,"jzmc":"星光岛（北京星光国际传媒有限公司）","address":"北京市朝阳区团结湖南里16号","postcode":"100026","ssjd":"团结湖街道","ssshequ":"南北里社区","ssxiaoqu":"南北里社区","louzuobianhao":"","jzwgd":"5.8","dsjzcs":"2","dxjzcs":"0","jsdw":"","jgsj":"1993-01-01","sjdw":"","sgdw":"","jldw":"","jzmj":"1189","jzjcxs":"","peopleCount":"12","tuzhi":"无","yt":"办公楼,","wenwudanwei":"否","kz":"","fldj":"","guifan":"89规范","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"","sgzl":"基本齐全","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"tj06","lon":116.472045,"lat":39.931191,"shuoming":"","qtcl":"","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243996000},{"id":1154569,"jzmc":"东辛店村293号","address":"朝阳区崔各庄乡东辛店村293号","postcode":"100102","ssjd":"崔各庄地区","ssshequ":"东辛店村","ssxiaoqu":"","louzuobianhao":"","jzwgd":"8","dsjzcs":"3","dxjzcs":"0","jsdw":"","jgsj":"2013-09-10","sjdw":"","sgdw":"","jldw":"","jzmj":"400","jzjcxs":"","peopleCount":"10","tuzhi":"无","yt":"住宅,","wenwudanwei":"","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"空调室外机,","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"否","uname":"cgz03","lon":116.508682,"lat":40.017428,"shuoming":"","qtcl":"砖墙","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243978000},{"id":1154568,"jzmc":"朝阳区孙河乡下辛堡村277号","address":"朝阳区孙河乡下辛堡村277号","postcode":"100103","ssjd":"孙河地区","ssshequ":"下辛堡村","ssxiaoqu":"下辛堡村","louzuobianhao":"","jzwgd":"3","dsjzcs":"1","dxjzcs":"0","jsdw":"个人自建","jgsj":"2000-01-01","sjdw":"个人自建","sgdw":"个人自建","jldw":"个人自建","jzmj":"400","jzjcxs":"","peopleCount":"5","tuzhi":"","yt":"住宅,","wenwudanwei":"否","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"非现浇板坡屋面","cdlx":"","zzwxw":"无","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"否","lmgz":"","uname":"sh06","lon":116.515415,"lat":40.068033,"shuoming":"","qtcl":"砖墙","ywql":"无圈梁","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243959000},{"id":1154567,"jzmc":"东辛店村162号","address":"朝阳区东辛店村162号","postcode":"100102","ssjd":"崔各庄地区","ssshequ":"东辛店村","ssxiaoqu":"","louzuobianhao":"","jzwgd":"6","dsjzcs":"3","dxjzcs":"0","jsdw":"","jgsj":"2007-05-30","sjdw":"","sgdw":"","jldw":"","jzmj":"500","jzjcxs":"","peopleCount":"9","tuzhi":"无","yt":"住宅,","wenwudanwei":"否","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"cgz03","lon":116.50507,"lat":40.014927,"shuoming":"","qtcl":"砖墙","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243943000},{"id":1154566,"jzmc":"双树北村279号","address":"北京市朝阳区黑庄户乡双树北村279号","postcode":"100121","ssjd":"黑庄户地区","ssshequ":"双树北村","ssxiaoqu":"双树北村","louzuobianhao":"1","jzwgd":"3.5","dsjzcs":"1","dxjzcs":"","jsdw":"","jgsj":"1969-03-01","sjdw":"","sgdw":"","jldw":"","jzmj":"288","jzjcxs":"","peopleCount":"10","tuzhi":"无","yt":"住宅,","wenwudanwei":"","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"空调室外机,","sgzl":"","ztjglf":"墙,","ztjglfqk":"大裂缝","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"否","uname":"hzh12","lon":116.606072,"lat":39.88632,"shuoming":"","qtcl":"砖墙","ywql":"无圈梁","ywgzz":"有构造柱","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243897000},{"id":1154565,"jzmc":"周井茂","address":"朝阳区王四营乡小海子17户","postcode":"100124","ssjd":"王四营地区","ssshequ":"观音堂村","ssxiaoqu":"朝阳区王四营乡小海子17户","louzuobianhao":"","jzwgd":"3.3","dsjzcs":"1","dxjzcs":"","jsdw":"","jgsj":"1993-06-01","sjdw":"","sgdw":"","jldw":"","jzmj":"320","jzjcxs":"独立基础,","peopleCount":"6","tuzhi":"无","yt":"住宅,","wenwudanwei":"否","kz":"不设防","fldj":"","guifan":"","jglx":"砖混,","ldlx":"非现浇板坡屋面","cdlx":"","zzwxw":"空调室外机,","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"否","uname":"xiaohaizi","lon":116.522228,"lat":39.89343,"shuoming":"","qtcl":"砖墙","ywql":"有圈梁","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243859000},{"id":1154564,"jzmc":"东辛店村161号","address":"朝阳区东辛店村161号","postcode":"100102","ssjd":"崔各庄地区","ssshequ":"东辛店村","ssxiaoqu":"","louzuobianhao":"","jzwgd":"6","dsjzcs":"3","dxjzcs":"0","jsdw":"","jgsj":"2008-07-20","sjdw":"","sgdw":"","jldw":"","jzmj":"500","jzjcxs":"","peopleCount":"9","tuzhi":"无","yt":"住宅,","wenwudanwei":"否","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"cgz03","lon":116.505125,"lat":40.014886,"shuoming":"","qtcl":"砖墙","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243843000},{"id":1154563,"jzmc":"朝阳区下新堡村372号","address":"朝阳区下新堡村372号","postcode":"100103","ssjd":"孙河地区","ssshequ":"下辛堡村","ssxiaoqu":"下新堡村","louzuobianhao":"","jzwgd":"3","dsjzcs":"1","dxjzcs":"0","jsdw":"个人自建","jgsj":"1986-01-01","sjdw":"个人自建","sgdw":"个人自建","jldw":"个人自建","jzmj":"470","jzjcxs":"","peopleCount":"8","tuzhi":"无","yt":"住宅,","wenwudanwei":"否","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"非现浇板坡屋面","cdlx":"","zzwxw":"无","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"否","lmgz":"","uname":"sh06","lon":116.514296,"lat":40.066801,"shuoming":"","qtcl":"砖墙","ywql":"无圈梁","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243821000},{"id":1154562,"jzmc":"东辛店村292号","address":"朝阳区崔各庄乡东辛店村292号","postcode":"100102","ssjd":"崔各庄地区","ssshequ":"东辛店村","ssxiaoqu":"","louzuobianhao":"","jzwgd":"8","dsjzcs":"3","dxjzcs":"0","jsdw":"","jgsj":"2013-09-10","sjdw":"","sgdw":"","jldw":"","jzmj":"400","jzjcxs":"","peopleCount":"10","tuzhi":"无","yt":"住宅,","wenwudanwei":"","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"空调室外机,","sgzl":"无","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"否","uname":"cgz03","lon":116.508736,"lat":40.017455,"shuoming":"","qtcl":"砖墙","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243777000},{"id":1154561,"jzmc":"西坝河西里社区8号楼","address":"朝阳区西坝河西里社区8号楼","postcode":"100028","ssjd":"香河园街道","ssshequ":"西坝河西里社区","ssxiaoqu":"西坝河西里社区","louzuobianhao":"","jzwgd":"55","dsjzcs":"16","dxjzcs":"2","jsdw":"","jgsj":"1985-08-01","sjdw":"","sgdw":"","jldw":"","jzmj":"10137","jzjcxs":"","peopleCount":"144","tuzhi":"","yt":"住宅,","wenwudanwei":"否","kz":"","fldj":"","guifan":"","jglx":"钢混,","ldlx":"","cdlx":"","zzwxw":"","sgzl":"基本齐全","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"xh05","lon":116.444516,"lat":39.971993,"shuoming":"","qtcl":"","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243770000},{"id":1154560,"jzmc":"双树北村280号","address":"北京市朝阳区黑庄户乡双树北村280号","postcode":"100121","ssjd":"黑庄户地区","ssshequ":"双树北村","ssxiaoqu":"双树北村","louzuobianhao":"1","jzwgd":"3.5","dsjzcs":"1","dxjzcs":"","jsdw":"","jgsj":"1977-10-01","sjdw":"","sgdw":"","jldw":"","jzmj":"270","jzjcxs":"","peopleCount":"5","tuzhi":"无","yt":"住宅,","wenwudanwei":"","kz":"","fldj":"","guifan":"","jglx":"砖混,","ldlx":"","cdlx":"","zzwxw":"空调室外机,","sgzl":"","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"否","uname":"hzh12","lon":116.605336,"lat":39.886576,"shuoming":"","qtcl":"砖墙","ywql":"无圈梁","ywgzz":"有构造柱","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243766000},{"id":1154559,"jzmc":"黑庄户乡苏坟村克劳沃物流中心5号库","address":"朝阳区黑庄户乡苏坟村克劳沃物流中心","postcode":"100121","ssjd":"黑庄户地区","ssshequ":"苏坟村","ssxiaoqu":"苏坟村","louzuobianhao":"5号库","jzwgd":"5","dsjzcs":"1","dxjzcs":"","jsdw":"","jgsj":"2008-01-01","sjdw":"","sgdw":"","jldw":"","jzmj":"330","jzjcxs":"桩基础,","peopleCount":"0","tuzhi":"有","yt":"仓库,","wenwudanwei":"否","kz":"八度设防","fldj":"","guifan":"","jglx":"钢结构,","ldlx":"岩棉彩钢板","cdlx":"","zzwxw":"无","sgzl":"齐全","ztjglf":"无","ztjglfqk":"","shifouweifang":"否","jiandingdanwei":"","pmgz":"是","lmgz":"","uname":"hzh15","lon":116.622264,"lat":39.886273,"shuoming":"","qtcl":"多种材料混合","ywql":"","ywgzz":"","sfjgkzjg":"否","kzjgsj":"","insertDate":1471243752000}]
     * currentPage : 1
     * totalRecord : 25275
     * totalPage : 1685
     */

    private int currentPage;
    private int totalRecord;
    private int totalPage;
    /**
     * id : 1154573
     * jzmc : 力鸿花园3号楼
     * address : 朝阳区左家庄中街6号院
     * postcode : 100028
     * ssjd : 左家庄街道
     * ssshequ : 左北里社区
     * ssxiaoqu : 力鸿花园
     * louzuobianhao : 3号楼
     * jzwgd : 70
     * dsjzcs : 24
     * dxjzcs : 2
     * jsdw : 北京民福房地产开发公司
     * jgsj : 1998-03-01
     * sjdw : 第二炮兵设计院
     * sgdw : 城建四公司
     * jldw :
     * jzmj : 26089
     * jzjcxs : 箱型基础,
     * peopleCount : 484
     * tuzhi : 有
     * yt : 住宅,
     * wenwudanwei : 否
     * kz : 八度设防
     * fldj : 丙类
     * guifan : 89规范
     * jglx : 钢混,
     * ldlx : 现浇板平屋面
     * cdlx : Ⅲ类
     * zzwxw : 空调室外机,
     * sgzl : 基本齐全
     * ztjglf : 无
     * ztjglfqk :
     * shifouweifang : 否
     * jiandingdanwei :
     * pmgz : 是
     * lmgz :
     * uname : zj03
     * lon : 116.446533
     * lat : 39.963945
     * shuoming :
     * qtcl : 多种材料混合
     * ywql : 有圈梁
     * ywgzz :
     * sfjgkzjg : 否
     * kzjgsj :
     * insertDate : 1471244094000
     */

    private List<DataRowsBean> dataRows;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataRowsBean> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<DataRowsBean> dataRows) {
        this.dataRows = dataRows;
    }

    public static class DataRowsBean implements Serializable{
        private String id;
        private String jzmc;
        private String address;
        private String postcode;
        private String ssjd;
        private String ssshequ;
        private String ssxiaoqu;
        private String louzuobianhao;
        private String jzwgd;
        private String dsjzcs;
        private String dxjzcs;
        private String jsdw;
        private String jgsj;
        private String sjdw;
        private String sgdw;
        private String jldw;
        private String jzmj;
        private String jzjcxs;
        private String peopleCount;
        private String tuzhi;
        private String yt;
        private String wenwudanwei;
        private String kz;
        private String fldj;
        private String guifan;
        private String jglx;
        private String ldlx;
        private String cdlx;
        private String zzwxw;
        private String sgzl;
        private String ztjglf;
        private String ztjglfqk;
        private String shifouweifang;
        private String jiandingdanwei;
        private String pmgz;
        private String lmgz;
        private String uname;
        private String lon;
        private String lat;
        private String shuoming;
        private String qtcl;
        private String ywql;
        private String ywgzz;
        private String sfjgkzjg;
        private String kzjgsj;
        private String zhengmian;
        private String cemian;
        private String beimian;
        private long insertDate;

        public String getZhengmian() {
            return zhengmian;
        }

        public void setZhengmian(String zhengmian) {
            this.zhengmian = zhengmian;
        }

        public String getCemian() {
            return cemian;
        }

        public void setCemian(String cemian) {
            this.cemian = cemian;
        }

        public String getBeimian() {
            return beimian;
        }

        public void setBeimian(String beimian) {
            this.beimian = beimian;
        }

        public String getId() {
            if (CommonUtil.isEmpty(id))
                return "";
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJzmc() {
            return jzmc;
        }

        public void setJzmc(String jzmc) {
            this.jzmc = jzmc;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getSsjd() {
            return ssjd;
        }

        public void setSsjd(String ssjd) {
            this.ssjd = ssjd;
        }

        public String getSsshequ() {
            return ssshequ;
        }

        public void setSsshequ(String ssshequ) {
            this.ssshequ = ssshequ;
        }

        public String getSsxiaoqu() {
            return ssxiaoqu;
        }

        public void setSsxiaoqu(String ssxiaoqu) {
            this.ssxiaoqu = ssxiaoqu;
        }

        public String getLouzuobianhao() {
            return louzuobianhao;
        }

        public void setLouzuobianhao(String louzuobianhao) {
            this.louzuobianhao = louzuobianhao;
        }

        public String getJzwgd() {
            return jzwgd;
        }

        public void setJzwgd(String jzwgd) {
            this.jzwgd = jzwgd;
        }

        public String getDsjzcs() {
            return dsjzcs;
        }

        public void setDsjzcs(String dsjzcs) {
            this.dsjzcs = dsjzcs;
        }

        public String getDxjzcs() {
            return dxjzcs;
        }

        public void setDxjzcs(String dxjzcs) {
            this.dxjzcs = dxjzcs;
        }

        public String getJsdw() {
            return jsdw;
        }

        public void setJsdw(String jsdw) {
            this.jsdw = jsdw;
        }

        public String getJgsj() {
            return jgsj;
        }

        public void setJgsj(String jgsj) {
            this.jgsj = jgsj;
        }

        public String getSjdw() {
            return sjdw;
        }

        public void setSjdw(String sjdw) {
            this.sjdw = sjdw;
        }

        public String getSgdw() {
            return sgdw;
        }

        public void setSgdw(String sgdw) {
            this.sgdw = sgdw;
        }

        public String getJldw() {
            return jldw;
        }

        public void setJldw(String jldw) {
            this.jldw = jldw;
        }

        public String getJzmj() {
            return jzmj;
        }

        public void setJzmj(String jzmj) {
            this.jzmj = jzmj;
        }

        public String getJzjcxs() {
            return jzjcxs;
        }

        public void setJzjcxs(String jzjcxs) {
            this.jzjcxs = jzjcxs;
        }

        public String getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(String peopleCount) {
            this.peopleCount = peopleCount;
        }

        public String getTuzhi() {
            return tuzhi;
        }

        public void setTuzhi(String tuzhi) {
            this.tuzhi = tuzhi;
        }

        public String getYt() {
            return yt;
        }

        public void setYt(String yt) {
            this.yt = yt;
        }

        public String getWenwudanwei() {
            return wenwudanwei;
        }

        public void setWenwudanwei(String wenwudanwei) {
            this.wenwudanwei = wenwudanwei;
        }

        public String getKz() {
            return kz;
        }

        public void setKz(String kz) {
            this.kz = kz;
        }

        public String getFldj() {
            return fldj;
        }

        public void setFldj(String fldj) {
            this.fldj = fldj;
        }

        public String getGuifan() {
            return guifan;
        }

        public void setGuifan(String guifan) {
            this.guifan = guifan;
        }

        public String getJglx() {
            return jglx;
        }

        public void setJglx(String jglx) {
            this.jglx = jglx;
        }

        public String getLdlx() {
            return ldlx;
        }

        public void setLdlx(String ldlx) {
            this.ldlx = ldlx;
        }

        public String getCdlx() {
            return cdlx;
        }

        public void setCdlx(String cdlx) {
            this.cdlx = cdlx;
        }

        public String getZzwxw() {
            return zzwxw;
        }

        public void setZzwxw(String zzwxw) {
            this.zzwxw = zzwxw;
        }

        public String getSgzl() {
            return sgzl;
        }

        public void setSgzl(String sgzl) {
            this.sgzl = sgzl;
        }

        public String getZtjglf() {
            return ztjglf;
        }

        public void setZtjglf(String ztjglf) {
            this.ztjglf = ztjglf;
        }

        public String getZtjglfqk() {
            return ztjglfqk;
        }

        public void setZtjglfqk(String ztjglfqk) {
            this.ztjglfqk = ztjglfqk;
        }

        public String getShifouweifang() {
            return shifouweifang;
        }

        public void setShifouweifang(String shifouweifang) {
            this.shifouweifang = shifouweifang;
        }

        public String getJiandingdanwei() {
            return jiandingdanwei;
        }

        public void setJiandingdanwei(String jiandingdanwei) {
            this.jiandingdanwei = jiandingdanwei;
        }

        public String getPmgz() {
            return pmgz;
        }

        public void setPmgz(String pmgz) {
            this.pmgz = pmgz;
        }

        public String getLmgz() {
            return lmgz;
        }

        public void setLmgz(String lmgz) {
            this.lmgz = lmgz;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getShuoming() {
            return shuoming;
        }

        public void setShuoming(String shuoming) {
            this.shuoming = shuoming;
        }

        public String getQtcl() {
            return qtcl;
        }

        public void setQtcl(String qtcl) {
            this.qtcl = qtcl;
        }

        public String getYwql() {
            return ywql;
        }

        public void setYwql(String ywql) {
            this.ywql = ywql;
        }

        public String getYwgzz() {
            return ywgzz;
        }

        public void setYwgzz(String ywgzz) {
            this.ywgzz = ywgzz;
        }

        public String getSfjgkzjg() {
            return sfjgkzjg;
        }

        public void setSfjgkzjg(String sfjgkzjg) {
            this.sfjgkzjg = sfjgkzjg;
        }

        public String getKzjgsj() {
            return kzjgsj;
        }

        public void setKzjgsj(String kzjgsj) {
            this.kzjgsj = kzjgsj;
        }

        public long getInsertDate() {
            return insertDate;
        }

        public void setInsertDate(long insertDate) {
            this.insertDate = insertDate;
        }
    }
}
