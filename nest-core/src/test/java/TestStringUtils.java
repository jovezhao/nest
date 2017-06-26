import com.jovezhao.nest.exception.CustomException;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.utils.JsonUtils;
import com.jovezhao.nest.utils.StringUtils;
import org.junit.Test;

/**
 * Created by Jove on 2016/11/21.
 */

public class TestStringUtils {
    class TestCustomException extends CustomException {

        public TestCustomException(int errorCode, String message, Object... arguments) {
            super(errorCode, message, arguments);
        }
    }

    @Test
    public void testJson()  {
        try{

        }finally {
            int a=3/0;
            System.out.println("fffff");
        }
    }

    @Test
    public void clear() {
        String text = StringUtils.clearHTML("\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<script type=\"text/javascript\">\n" +
                "  var pvinsight_page_ancestors = '143746642;143746653;212846158;223864775;473732465';\n" +
                "</script>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=gb2312\" />\n" +
                "<title>中央军委印发《加强实战化军事训练暂行规定》-搜狐新闻</title>\n" +
                "<meta name=\"keywords\" content=\"实战 军委\">\n" +
                "<meta name=\"description\" content=\"新华社北京11月21日电（黎云、吴旭）经习近平主席批准，中央军委近日印发《加强实战化军事训练暂行规定》，对落实实战化军事训练提出刚性措施、作出硬性规范。《规定》\">\n" +
                "<link rel=\"canonical\" href=\"http://news.sohu.com/20161121/n473732465.shtml\"/>\n" +
                "<meta name=\"robots\" content=\"all\">\n" +
                "<!-- 购买url变量获取 -->\n" +
                "<script type=\"text/javascript\">\n" +
                " var shoppingCnf = {url:''};\n" +
                "</script>\n" +
                "<!-- 购买url变量获取end -->\n" +
                "<script type=\"text/javascript\">\n" +
                "  var category =  '143746642;143746653;212846158;223864775;473732465';  var videoNum = 0;  var relate_from_sohu = true;\n" +
                "</script>\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\" />\n" +
                "<script src=\"http://www.sohu.com/sohuflash_1.js\" type=\"text/javascript\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://js.sohu.com/library/jquery-1.7.1.min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://news.sohu.com/upload/article/2012/js/tongji_v2013110101.js\"></script>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "jQuery(function(jq){\n" +
                "\t//标签\n" +

                "}\n" +
                " \n" +
                "</script>\n" +
                "<script src=\"http://img.wan.sogou.com/cdn/ufo/fid/fid.js\"></script>\n" +
                "<script>\n" +
                "    var fid;\n" +
                "    Sogou_FID.get(function(sid){\n" +
                "        fid = sid;       \n" +
                "    });\n" +
                "</script>\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"http://css.sohu.com/upload/global1.4.1.css\" />\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"http://news.sohu.com/upload/article/2012/style.v20150623.css\" />\n" +
                "<style>\n" +
                ".original-tit{font-size:14px;line-height:24px;padding-left:28px;}\n" +
                "</style>\n" +
                "\n" +
                "<script  type='text/javascript'>\n" +
                "var sogou_is_brand = new Object();\n" +
                "sogou_is_brand[\"pid\"]  = 'sohu__brand';\n" +
                "sogou_is_brand[\"charset\"]   = 'gb2312';\n" +
                "sogou_is_brand[\"sohuurl\"] = document.location.href;\n" +
                "</script>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div data-type=\"roll\" data-appid=\"1079\" id=\"isohu-topbar\"></div>\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"http://news.sohu.com/upload/itoolbar/cms/itoolbar.201410281438.css\" charset=\"UTF-8\" />\n" +
                "<style type=\"text/css\"> \n" +
                "#innerToolBar{width:980px;}\n" +
                "</style>\n" +
                "<script src=\"http://news.sohu.com/upload/itoolbar/itoolbar.cms.loader.201410281438.js\" charset=\"UTF-8\"></script>\n" +
                "\n" +
                "<!-- 导航 st -->\n" +
                "<div id=\"nav\" class=\"area\"><a class=\"first\" href=\"http://www.sohu.com/\">首页</a>-<a href=\"http://news.sohu.com/\">新闻</a>-<a class=\"n3\" href=\"http://mil.sohu.com/\">军事</a>-<a href=\"http://cul.sohu.com/\">文化</a>-<a href=\"http://history.sohu.com/\">历史</a>-<a class=\"n3\" href=\"http://sports.sohu.com/\">体育</a>-<a href=\"http://sports.sohu.com/nba.shtml\">NBA</a>-<a href=\"http://tv.sohu.com/\">视频</a>-<a class=\"n3\" href=\"http://yule.sohu.com/\">娱乐</a>-<a href=\"http://business.sohu.com/\">财经</a>-<a href=\"http://stock.sohu.com/\">股票</a>-<a class=\"n3\" href=\"http://it.sohu.com/\">科技</a>-<a href=\"http://auto.sohu.com/\">汽车</a>-<a href=\"http://www.focus.cn\">房产</a>-<a class=\"n3\" href=\"http://fashion.sohu.com/\">时尚</a>-<a href=\"http://health.sohu.com/\">健康</a>-<a href=\"http://learning.sohu.com/\">教育</a>-<a class=\"n3\" href=\"http://baobao.sohu.com/\">母婴</a>-<a href=\"http://travel.sohu.com/\">旅游</a>-<a href=\"http://chihe.sohu.com/\">美食</a>-<a class=\"last\" href=\"http://astro.women.sohu.com/\">星座</a></div>\n" +
                "<!-- 导航 end -->\n" +
                "\n" +
                "\n" +
                "<!-- 翻牌广告 st -->\n" +
                "<div id=\"turn-ad\" class=\"area\">\n" +
                "\t<div class=\"left\">\n" +
                "\t\t<SOHUADCODE><script type=\"text/javascript\" src=\"http://images.sohu.com/bill/s2012/gates/all/sohuad2012v15.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://txt.go.sohu.com/ip/soip\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://images.sohu.com/bill/tongyong/js/top/Topnewsthird950100V20140925.js\" ></script><div>\n" +
                "</div>\n" +
                "</SOHUADCODE>\n" +
                "\t</div>\n" +
                "\t<div class=\"center\">\n" +
                "\t\t<SOHUADCODE><script> var config = { passion:{ PAGEID : \"news-article\" , CHANNELID : \"2\"} }; </script>\n" +
                "<script src=\"http://images.sohu.com/saf/static/0/1000060.shtml\"></script>\n" +
                "<script src=\"http://txt.go.sohu.com/ip/soip\"></script>\n" +
                "<script src=\"http://images.sohu.com/bill/s2013/gates/js/Sohu-0.3.8.min.js\"></script>\n" +
                "<script src=\"http://images.sohu.com/bill/default/sohu-require.js\"></script><div class=TurnAD20> \n" +
                "<div id=TurnAD20 width=970 height=90>\n" +
                " <script language=javascript>\n" +
                "try{\n" +
                "var TurnAD20=new Cookie(document,\"TurnAD20\",24);\n" +
                "TurnAD20.load();\n" +
                "TurnAD20.visit=(TurnAD20.visit==null)?parseInt(Math.random()*2+1):TurnAD20.visit;\n" +
                "if(TurnAD20.visit!=0)var intTurnAD20=TurnAD20.visit;\n" +
                "TurnAD20.visit++;\n" +
                "TurnAD20.visit=(TurnAD20.visit>2)?1:TurnAD20.visit;\n" +
                "TurnAD20.store();\n" +
                "function showTurnAD20(basenum){\n" +
                "if (basenum==1){\n" +
                "if (typeof has_topAd == \"undefined\" || has_topAd==0) {\n" +
                "//topnews代码，投放时请带上，轮换代码从下面开始替换\n" +
                "\n" +
                "document.getElementById('TurnAD20').innerHTML ='<iframe width=970 height=90 marginwidth=0 marginheight=0 hspace=0 frameborder=0 scrolling=no bordercolor=#000000 src=  http://static-alias-1.360buyimg.com/jzt/tpl/sspPic.html?ad_ids=2427:5&adflag=0&clkmn=&expose=></iframe>';\n" +
                "//轮换代码结束\n" +
                "}\n" +
                "function addImp(adobj){\n" +
                "\t\t\t\tvar img = new Image();\n" +
                "\t\t\t\timg.onload = function(){\n" +
                "\t\t\t\t\timg = null;\n" +
                "\t\t\t\t};\n" +
                "\t\t\t\timg.src = adobj;\n" +
                "\t\t\t}\n" +
                "addImp(\"http://imp.optaim.com/201507/9613d5ba8f97474f83901795d19d2686.php?a=0\");}\n" +
                "else{\n" +
                "if (typeof has_topAd == \"undefined\" || has_topAd==0) {\n" +
                "//topnews代码，投放时请带上，轮换代码从下面开始替换\n" +
                "\n" +
                "require([\"sjs/matrix/ad/passion\"], function (passion) {\n" +
                "    var _C = \"#TurnAD20\",_ID = \"10013\",_W = 970,_H = 90,_T = 2,_F=201;\n" +
                "    if(_C){jQuery(_C).attr('id','beans_'+_ID).css({'width' : _W + 'px', 'height' : _H + 'px'});}\n" +
                "    passion.ones({itemspaceid : _ID,width:_W,height:_H,adsrc : _F,turn : _T,defbeans : !0});\n" +
                "    });\n" +
                "//轮换代码结束\n" +
                "}}\n" +
                "}\n" +
                "showTurnAD20(intTurnAD20);\n" +
                "}catch(e){}\n" +
                "</script>\n" +
                "</div>\n" +
                "</div>\n" +
                "</SOHUADCODE>\n" +
                "\t</div>\n" +
                "\t<div class=\"right\">\n" +
                "\t\t<SOHUADCODE><div>\n" +
                "</div>\n" +
                "</SOHUADCODE>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<!-- 翻牌广告 end -->\n" +
                "\n" +
                "\n" +
                "<!-- 频道导航 st -->\n" +
                "<div id=\"channel-nav\" class=\"area\">\n" +
                "\t<div id=\"mypos\" class=\"left\" itemprop=\"mypos\"><a href=\"http://news.sohu.com/\" target=\"_blank\"><img src=\"http://news.sohu.com/upload/pagerevision20090916/news_logo3.gif\" width=\"110\" height=\"24\" alt=\"\" /></a><span>&gt; <a href=http://news.sohu.com/1/0903/61/subject212846158.shtml>国内要闻</a> &gt; <a href=http://news.sohu.com/s2005/shishi.shtml>时事</a></span></div>\n" +
                "\t<div class=\"navigation\" style=\"display:none\" ><a href=http://news.sohu.com/>新闻中心</a> &gt; <a href=http://news.sohu.com/guoneixinwen.shtml>国内新闻</a> &gt; <a href=http://news.sohu.com/1/0903/61/subject212846158.shtml>国内要闻</a> &gt; <a href=http://news.sohu.com/s2005/shishi.shtml>时事</a></div>\n" +
                "\t<div class=\"right\"><a href=\"http://news.sohu.com/guoneixinwen.shtml\" target=\"_blank\">国内</a> | <a href=\"http://news.sohu.com/guojixinwen.shtml\" target=\"_blank\">国际</a> | <a href=\"http://news.sohu.com/shehuixinwen.shtml\" target=\"_blank\">社会</a> | <a href=\"http://mil.news.sohu.com/\" target=\"_blank\">军事</a> | <a href=\"http://star.sohu.com/\" target=\"_blank\">评论</a>\n" +
                "\n" +
                "\n" +
                "<style>\n" +
                "\n" +
                "                   #channel-nav{position:relative;}\n" +
                "\n" +
                "                   #channel-nav .logo-ad{position:absolute; left:490px; top:5px;}\n" +
                "\n" +
                "         </style>\n" +
                "\n" +
                "         <div class=\"logo-ad\">\n" +
                "\n" +
                "                   \n" +
                "         </div></div>\n" +
                "</div>\n" +
                "<!-- 频道导航 end -->\n" +
                "<div id=\"container\" class=\"area\" itemscope itemtype=\"http://schema.org/NewsArticle\" itemref=\"mypos\">\n" +
                "\t<div class=\"content-wrapper grid-675\">\n" +
                "\t\t<!-- 广告 -->\n" +
                "\t\t<div class=\"ad675\">\n" +
                "\t\t\t<SOHUADCODE>\n" +
                "</SOHUADCODE>\n" +
                "\t\t</div>\t\t\n" +
                "\t\t<div class=\"content-box clear\">\n" +
                "\t\t\t<!-- 文章标题 -->\n" +
                "\t\t\t<h1 itemprop=\"headline\">中央军委印发《加强实战化军事训练暂行规定》</h1>\n" +
                "\t\t\t<!-- 正文标签 -->\n" +
                "\t\t\t<div class=\"label-ad clear\" id=\"commentTab\">\n" +
                "\t\t\t\t<div class=\"label\">\n" +
                "\t\t\t\t\t<ul>\n" +
                "\t\t\t\t\t\t<li class=\"text-label\" itemprop=\"isHot\" content=\"true\"><em>正文</em></li>\n" +
                "\t\t\t\t\t\t<li class=\"com-label\"><em><a href=\"http://pinglun.sohu.com/s473732465.html\" itemprop=\"discussionUrl\">我来说两句<span class=\"f12\">(<span class=\"red\" id=\"changyan_parti_unit\"><img src=\"http://comment.news.sohu.com/upload/comment4/images/loading_blue.gif\" width=\"16px\" height=\"16px\" /></span>人参与)</span></a></em></li>\n" +
                "\t\t\t\t\t</ul>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"ad\">\n" +
                "\t\t\t\t\t<div class=\"adFrag\"><SOHUADCODE>\n" +
                "</SOHUADCODE></div>\n" +
                "\t\t\t\t\t<div class=\"scan-handset\" id=\"scan-handset\"><a class=\"scan-icon\" href=\"javascript:void(0)\">扫描到手机</a></div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"scan-layer\" id=\"scan-layer-id\">\n" +
                "\t\t\t\t\t<div class=\"scan-pic\"><img width=\"99\" height=\"99\" alt=\"\" src=\"http://news.sohu.com/upload/article/2012/images/pic/scan_pic_98x98.gif\"></div>\n" +
                "\t\t\t\t\t<div class=\"scan-layer-close\"><a href=\"javascript:void(0)\">关闭</a></div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<script type=\"text/javascript\">\n" +
                "\t\t\t\t\tjQuery(function(jq){\n" +
                "\t\t\t\t\t    if(jq(\"#scan-layer-id .scan-pic img\").length > 0){\n" +
                "\t                           var theU = \"http://s1.rr.itc.cn/qrcode/m/n/\"+473732465+\".png\";\n" +
                "\t                           jq(\"#scan-layer-id .scan-pic img\").attr(\"src\",theU);\n" +
                "\t                    }\n" +
                "\t\t\t\t\t\tjq(\"#scan-handset\").click(function(){\n" +
                "\t\t\t\t\t\t\tjq(\"#scan-layer-id\").show();\n" +
                "\t\t\t\t\t\t});\n" +
                "\t\t\t\t\t\tjq(\"#scan-layer-id .scan-layer-close a\").click(function(){\n" +
                "\t\t\t\t\t\t\tjq(\"#scan-layer-id\").hide();\n" +
                "\t\t\t\t\t\t});\n" +
                "\t\t\t\t\t});\n" +
                "\t\t\t\t</script>\n" +
                "\t\t\t</div>\t\t\t\t\n" +
                "\t\t\t<!-- 时间 来源 -->\n" +
                "\t\t    <div class=\"time-fun clear\">\n" +
                "\t\t\t\t<div class=\"time-source\">\n" +
                "\t\t\t\t\t<div class=\"time\" id=\"pubtime_baidu\" itemprop=\"datePublished\" content=\"2016-11-21T15:39:27+08:00\">2016-11-21 15:39:27</div>\t\t\t\t\t\t\n" +
                "\t\t\t\t\t<div class=\"source\">\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<span class=\"sc\" id=\"source_baidu\" >来源：<span id=\"media_span\" itemprop=\"publisher\" itemscope itemtype=\"http://schema.org/Organization\"><span itemprop=\"name\">新华网</span></span></span>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t</div>\t\t\t\t\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<style>\n" +
                "\t\t\t\t#container .time-fun .time-source{width:420px}\n" +
                "\t\t\t\t#container .time-fun .time-source .source{width:293px}\n" +
                "\t\t\t\t#container .time-fun .function .phone-ctcp a{padding-left:16px;background:url(http://news.sohu.com/upload/article/2012/images20140613/down.gif) no-repeat 0 1px}\n" +
                "\t\t\t\t</style>\n" +
                "                <div class=\"function\">\n" +
                "\t\t\t\t\t<ul>\n" +
                "\t\t\t\t\t\t<li class=\"share-li\" style=\"background-position: right 0!important;margin-right: 5px!important;padding-right: 4px!important;padding-left:0!important\">\n" +
                "\t\t\t\t\t\t\t<style>\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm{float:right;}\n" +
                "\t\t\t\t\t\t\t\t#container .share ul{float:right;}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm li{float:left;margin:0 5px 0 0;display:inline;padding:0!important;}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm li a{background:url(http://i3.itc.cn/20160325/3649_a4efbd35_27d1_0290_fe74_2c9efe4cf22e_1.png) no-repeat;padding:0!important;}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm li a{float:left;height:16px;width:16px;display:block;}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm .sina a{background-position:0 0}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm .weixin a{background-position: -20px 0}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm .qzone a{background-position: -41px 0}\n" +
                "\t\t\t\t\t\t\t\t#container .share.sm .rr a{background-position: -62px 0}\n" +
                "\t\t\t\t\t\t\t</style>\n" +
                "\t\t\t\t\t\t\t<div class=\"share clear sm\" id=\"share\">\n" +
                "\t\t\t\t              <ul>\n" +
                "\t\t\t\t                   <li class=\"sina\"><a href=\"javascript:void(0)\" onclick=\"dispatch('.bds_tsina')\" title=\"分享到新浪微博\"></a><em></em></li>\n" +
                "\t\t\t\t                   <li class=\"weixin\"><a href=\"javascript:void(0)\" onclick=\"dispatch('.bds_weixin')\" t`itle=\"分享给微信好友\"></a><em></em></li>\n" +
                "\t\t\t\t                   <li class=\"qzone\"><a href=\"javascript:void(0)\" onclick=\"jumpUrl('qq')\" title=\"分享给QQ好友\"></a><em></em></li>\n" +
                "\t\t\t\t                   <li class=\"rr\"><a href=\"javascript:void(0)\" onclick=\"jumpUrl('renren')\" title=\"分享到人人网\"></a></li>\n" +
                "\t\t\t\t         \t\t</ul>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</li>\n" +
                "\t\t\t\t\t\t<li class=\"phone-ctcp\"><a href=\"http://k.sohu.com/\" target=\"_blank\">手机看新闻</a></li>\n" +
                "\t\t\t\t\t\t<li class=\"fontsize\"><a href=\"javascript:void(0)\"></a>\n" +
                "\t\t\t\t\t\t\t<div class=\"fontsize-layer\"><a href=\"javascript:doZoom(16)\">大</a>|<a href=\"javascript:doZoom(14)\">中</a>|<a href=\"javascript:doZoom(12)\">小</a></div>\n" +
                "\t\t\t\t\t\t</li>\n" +
                "\t\t\t\t\t\t<li class=\"print\"><a href=\"javascript:printArticle()\"></a>\n" +
                "\t\t\t\t\t\t\t<div class=\"print-layer\"><a href=\"javascript:printArticle()\">打印</a></div>\n" +
                "\t\t\t\t\t\t</li>                                      \n" +
                "\t\t\t\t\t</ul>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\n" +
                "\t\t\t<div class=\"original-tit\">\n" +
                "\t\t\t\t    原标题：经习近平主席批准 中央军委印发《加强实战化军事训练暂行规定》\n" +
                "\t\t\t         </div>\n" +
                "\t\t\t\n" +
                "\t\t\t<SOHUADCODE>\n" +
                "</SOHUADCODE>\n" +
                "\t\t\t\n" +
                "            \n" +
                "            \n" +
                "\t\t\t\n" +
                "\t\t\t<!-- 正文 -->\n" +
                "\t\t\t<div class=\"text clear\" id=\"contentText\">\n" +
                "\t\t\t\t<div itemprop=\"articleBody\"><p>　　新华社北京11月21日电（黎云、吴旭）经习近平主席批准，中央军委近日印发《加强实战化军事训练暂行规定》，对落实实战化军事训练提出刚性措施、作出硬性规范。</p>\n" +
                "<p>　　《规定》以习主席关于实战化军事训练的系列重要指示为指导，明确了实战化军事训练的基本内涵和总体要求，进一步统一全军对实战化军事训练的认识，强调实战化军事训练是从实战需要出发从难从严进行的训练，是军事训练的基本要求，是推进训练与实战达到一体化的重要保证，要求全军必须把实战化贯穿渗透于军事训练全过程各领域。</p>\n" +
                "<p>　　《规定》明确，要着眼纠治当前军事训练中的突出问题，规范实战化军事训练组织实施，强调要依据敌情、我情、战场环境开展训练，保证联合训练落实，持续推进战法创新，规范战役战术训练程序，抓好指挥员训练，落实主官任教、按级任教，改进实兵检验性演习，加大训练难度强度。要着眼推动训风演风考风进一步转变，解决训练作假、图名挂号争彩头和考核软比武滥拉动简单等问题，严格落实训练普考制度，严密组织考核比武拉动。要着眼科学调配训练保障资源，进一步提高实战化军事训练的综合保障效益，加强训练安全管理，改进训练宣传报道。</p>\n" +
                "<p>　　《规定》强调，各级党委要强化抓训责任，坚持训练中心地位、落实议训制度，实行训练成绩“一票否决”；各级军事训练监察部门和纪检监察部门要发挥职能作用，加强专项监察；对违反《规定》的情形要按照有关法规及时作出处罚。</p>\n" +
                "<p>　　《规定》要求，全军和武警部队要结合实际制定落实措施，细化有关标准要求，抓好贯彻执行，不断提高军事训练实战化水平。</p><script type=\"text/javascript\">// <![CDATA[\n" +
                "media_span_url('http://news.xinhuanet.com/politics/2016-11/21/c_1119955248.htm')\n" +
                "//]]></script></div>\n" +
                "                \n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<!-- seo标签描述 -->\n" +
                "\t\t\t\t<div style=\"display:none;\">\n" +
                "\t\t\t\t\t<span id=\"url\" itemprop=\"url\">http://news.sohu.com/20161121/n473732465.shtml</span>\n" +
                "\t\t\t\t\t<span id=\"indexUrl\" itemprop=\"indexUrl\">news.sohu.com</span>\n" +
                "\t\t\t\t\t<span id=\"isOriginal\" itemprop=\"isOriginal\">false</span>\n" +
                "\t\t\t\t\t<span id=\"sourceOrganization\" itemprop=\"sourceOrganization\" itemscope itemtype=\"http://schema.org/Organization\"><span itemprop=\"name\">新华网</span></span>\n" +
                "\t\t\t\t\t<span id=\"author\" itemprop=\"author\" itemscope itemtype=\"http://schema.org/Organization\"><span itemprop=\"name\"></span></span>\n" +
                "\t\t\t\t\t<span id=\"isBasedOnUrl\" itemprop=\"isBasedOnUrl\">http://news.xinhuanet.com/politics/2016-11/21/c_1119955248.htm</span>\n" +
                "\t\t\t\t\t<span id=\"genre\" itemprop=\"genre\">report</span>\n" +
                "\t\t\t\t\t<span id=\"wordCount\" itemprop=\"wordCount\">828</span>\n" +
                "\t\t\t\t\t<span id=\"description\" itemprop=\"description\">新华社北京11月21日电（黎云、吴旭）经习近平主席批准，中央军委近日印发《加强实战化军事训练暂行规定》，对落实实战化军事训练提出刚性措施、作出硬性规范。《规定》</span>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\t\n" +
                "           <div class=\"original-title\"><span class=\"editer\" id=\"editor_baidu\">(责任编辑：窦远行 UN833)</span>\n" +
                "\t\t\t</div>\t\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t<!-- 底部分享 -->\n" +
                "\t\t\t<div class=\"bdsharebuttonbox\" style=\"display:none!important\"><a href=\"#\" class=\"bds_more\" data-cmd=\"more\"></a><a href=\"#\" class=\"bds_qzone\" data-cmd=\"qzone\" title=\"分享到QQ空间\"></a><a href=\"#\" class=\"bds_tsina\" data-cmd=\"tsina\" title=\"分享到新浪微博\"></a><a href=\"#\" class=\"bds_renren\" data-cmd=\"renren\" title=\"分享到人人网\"></a><a href=\"#\" class=\"bds_weixin\" data-cmd=\"weixin\" title=\"分享到微信\"></a></div>\n" +
                "\t\t\t<script>window._bd_share_config={\"common\":{\"bdSnsKey\":{},\"bdText\":\"\",\"bdMini\":\"2\",\"bdMiniList\":false,\"bdPic\":\"\",\"bdStyle\":\"0\",\"bdSize\":\"16\"},\"share\":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>\n" +
                "\t\t\t<style>\n" +
                "\t\t\t\t#container .share{margin:0 auto;padding:0 0 20px;height:26px;position:relative}\n" +
                "\t\t\t\t#container .share .editer{float:left;margin-top:5px}\n" +
                "\t\t\t\t#container .share ul{float:right;}\n" +
                "\t\t\t\t#container .share li{float:left;margin:0 5px 0 0;display:inline}\n" +
                "\t\t\t\t#container .share.lg li a{background:url(http://i3.itc.cn/20160325/3649_a4efbd35_27d1_0290_fe74_2c9efe4cf22e_1.png) no-repeat;}\n" +
                "\t\t\t\t#container .share.lg li a{float:left;height:26px;width:26px;display:block;}\n" +
                "\t\t\t\t#container .share.lg .sina a{background-position:0 -56px}\n" +
                "\t\t\t\t#container .share.lg .weixin a{background-position: -31px -56px}\n" +
                "\t\t\t\t#container .share.lg .qzone a{background-position: -63px -56px}\n" +
                "\t\t\t\t#container .share.lg .rr a{background-position: -94px -56px}\n" +
                "\t\t\t</style>\n" +
                "\t\t\t<div class=\"share clear lg\" id=\"share\">\n" +
                "\t\t\t  <ul>\n" +
                "\t\t\t       <li class=\"share-tt\">分享：</li>\n" +
                "\t\t\t       <li class=\"sina\"><a href=\"javascript:void(0)\" onclick=\"dispatch('.bds_tsina')\" title=\"分享到新浪微博\"></a><em></em></li>\n" +
                "\t\t\t       <li class=\"weixin\"><a href=\"javascript:void(0)\" onclick=\"dispatch('.bds_weixin')\" title=\"分享给微信好友\"></a><em></em></li>\n" +
                "\t\t\t       <li class=\"qzone\"><a href=\"javascript:void(0)\" onclick=\"jumpUrl('qq')\" title=\"分享给QQ好友\"></a><em></em></li>\n" +
                "\t\t\t       <li class=\"rr\"><a href=\"javascript:void(0)\" onclick=\"jumpUrl('renren')\" title=\"分享到人人网\"></a></li>\n" +
                "\t\t\t\t\t</ul>\n" +
                "\t\t\t\t<div class=\"go_sohu_home\"><a href=\"http://www.sohu.com\" target=\"_blank\" onclick=\"toHomePage();\"></a></div>\n" +
                "\t\t\t\t<script type=\"text/javascript\">\n" +
                "\t\t\t\t\tfunction dispatch(arg){\n" +
                "\t\t\t\t\t\tvar el = $(arg)[0];\n" +
                "\t\t\t\t\t\tvar evt = document.createEvent('Event');    \n" +
                "\t\t\t\t        evt.initEvent(\"click\",true,true);    \n" +
                "\t\t\t\t        el.dispatchEvent(evt);    \n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t</script>\n" +
                "\t\t\t\t<style>\n" +
                "\t\t\t\t\t#container .share .world-link{float:left;display:inline;margin:5px 0 0 160px;line-height:20px}\n" +
                "\t\t\t\t\t#container .share .world-link a{padding-left:16px;display:inline-block;background:url(http://news.sohu.com/upload/article/2012/images20140613/down.gif) no-repeat 0 2px}\n" +
                "\t\t\t\t</style>\n" +
                "\t\t\t\t<div class=\"world-link\"><a href=\"http://k.sohu.com\" target=\"_blank\">手机看新闻</a></div>    \t\t\t\t\t\t\n" +
                "\t\t\t</div>\t\n" +
                "\t\t\t<!-- 底部分享结束 -->\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t<!-- 本文相关推荐-->\n" +
                "            <!-- 其它频道 -->\n" +
                "<style>\n" +
                "#container .tagHotg .tagIntg li a{color:#000}\n" +
                "#container .tagHotg .tagIntg li a:hover{color:#c00}\n" +
                "</style>\n" +
                "\t\t\t<style>\n" +
                "/* 相关热点 2010-09-09 */\n" +
                "#contentA .tagHotg{clear:both;margin:10px auto 0;text-align:left}\n" +
                "#contentA .tagHotg h3{height:24px;font:600 14px/24px \"宋体\";color:#333}\n" +
                "#contentA .tagHotg .tagIntg{display: block;overflow:hidden;zoom:1;clear:both;width:100%;margin:3px auto 0}\n" +
                "#contentA .tagHotg .tagIntg li{float:left;width:205px;font:14px/24px \"宋体\"}\n" +
                "#contentA .tagHotg .tagIntg li a{ text-decoration:none !important;}\n" +
                "#contentA .tagHotg .tagIntg li a:hover{text-decoration:underline !important;}\n" +
                "#contentA .text .line{width:100%}\n" +
                "</style>\n" +
                "<div class=\"tagHotg\">\n" +
                "  <h3><a href=\"http://www.sogou.com/\" target=\"_blank\">本文相关推荐</a></h3>\n" +
                "  <div class=\"tagIntg\">\n" +
                "    <ul>\n" +
                "                        <li><a class=\"hot-icon\" href=\"http://www.sogou.com/web?query=%E5%8D%B0%E5%BA%A6%E6%AF%94%E4%B8%AD%E5%9B%BD%E5%86%9B%E4%BA%8B%E5%BC%BA%E5%A4%9A%E5%B0%91&ie=utf8&p=02210102&fhintidx=0\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len\n" +
                "=\"12\" ind=\"1\">印度比中国军事强多少</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E4%B8%AD%E5%9B%BD%E5%8D%B0%E5%BA%A6%E5%86%9B%E4%BA%8B%E5%AE%9E%E5%8A%9B%E5%B7%AE%E8%B7%9D&ie=utf8&p=02210102&fhintidx=1\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"2\">\n" +
                "中国印度军事实力差距</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E5%86%9B%E4%BA%8B%E6%96%B0%E9%97%BB%E5%8D%B0%E5%BA%A6%E5%BC%80%E6%88%98%E4%B8%AD%E5%9B%BD&ie=utf8&p=02210102&fhintidx=2\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"3\">\n" +
                "军事新闻印度开战中国</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E6%8F%90%E9%AB%98%E5%86%9B%E4%BA%8B%E8%AE%AD%E7%BB%83%E5%AE%9E%E6%88%98%E5%8C%96%E6%B0%B4%E5%B9%B3&ie=utf8&p=02210102&fhintidx=3\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"4\">\n" +
                "提高军事训练实战化水平</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E7%9B%9B%E5%94%90%E5%86%9B%E4%BA%8B%E7%BD%91%E6%9C%80%E6%96%B0%E5%86%9B%E4%BA%8B&ie=utf8&p=02210102&fhintidx=4\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"5\">\n" +
                "盛唐军事网最新军事</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E4%B8%AD%E5%A4%AE%E5%86%9B%E4%BA%8B%E5%A7%94%E5%91%98%E4%BC%9A&ie=utf8&p=02210102&fhintidx=5\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"6\">\n" +
                "中央军事委员会</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E4%B8%AD%E5%9B%BD%E5%8F%A4%E4%BB%A3%E5%86%9B%E4%BA%8B%E6%80%9D%E6%83%B3%E8%90%8C%E8%8A%BD%E4%BA%8E&ie=utf8&p=02210102&fhintidx=6\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"7\">\n" +
                "中国古代军事思想萌芽于</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E5%86%9B%E4%BA%8B%E9%98%9F%E5%88%97%E8%AE%AD%E7%BB%83%E8%AE%A1%E5%88%92&ie=utf8&p=02210102&fhintidx=7\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"8\">\n" +
                "军事队列训练计划</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E5%86%9B%E4%BA%8B%E9%AB%98%E6%8A%80%E6%9C%AF%E7%9A%84%E7%89%B9%E5%BE%81%E4%B8%8D%E5%8C%85%E6%8B%AC&ie=utf8&p=02210102&fhintidx=8\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"9\">\n" +
                "军事高技术的特征不包括</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E5%AD%A6%E7%94%9F%E5%86%9B%E4%BA%8B%E8%AE%AD%E7%BB%83%E5%B7%A5%E4%BD%9C%E8%A7%84%E5%AE%9A&ie=utf8&p=02210102&fhintidx=9\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"10\">\n" +
                "学生军事训练工作规定</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E5%90%8E%E5%8B%A4%E5%AE%9E%E6%88%98%E5%8C%96%E8%AE%AD%E7%BB%83%E7%BA%AA%E5%AE%9E&ie=utf8&p=02210102&fhintidx=10\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"11\">\n" +
                "后勤实战化训练纪实</a></li>\n" +
                "                                <li><a href=\"http://www.sogou.com/web?query=%E5%A6%82%E4%BD%95%E5%8A%A0%E5%BC%BA%E5%86%9B%E4%BA%8B%E5%AE%9E%E5%8A%9B&ie=utf8&p=02210102&fhintidx=11\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" onclick=\"return sogouRelateWeb(this);\" len=\"12\" ind=\"12\">\n" +
                "如何加强军事实力</a></li>\n" +
                "                                </ul>\n" +
                "  </div>\n" +
                "</div>\n" +
                "\t\t\t<script type=\"text/javascript\">var entityId = 473732465;</script>\t\n" +
                "\t\t\t\n" +
                "\t\t\t\t<div id=\"SOHUCS\" sid=\"\"></div>\n" +
                "<script>\n" +
                "document.domain = \"sohu.com\";\n" +
                "if(typeof entityId != 'undefined'){\n" +
                "    document.getElementById('SOHUCS').setAttribute('sid',entityId); \n" +
                "    var category = typeof category != 'undefined' ? category :'';                 \n" +
                "    var _config = {\n" +
                "                   hideList: true,               \n" +
                "                   hideSubList: true,            \n" +
                "                    jumpUrl: 'http://pinglun.sohu.com/s[topicId].html',\n" +
                "                    varName: entityId, \n" +
                "                    customSohu: true,\n" +
                "                    cyanTitle : '我来说两句',\n" +
                "                    commentHide : true ,\n" +
                "                    pageSize: 10,\n" +
                "                    categoryId: category\n" +
                "                }\n" +
                "    }\n" +
                "    window.SCS_NO_IFRAME = true;\n" +
                "</script>\n" +
                "\n" +
                "<script src=\"http://assets.changyan.sohu.com/upload/changyan.js?appid=cyqemw6s1&conf=prod_0266e33d3f546cb5436a10798e657d97\" charset=\"utf-8\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://assets.changyan.sohu.com/upload/plugins/plugins.count.js\"></script>\n" +
                "\t\t\t\t\n" +
                "\t\t\t<!--图文转载-->\n" +
                "\t\t\t<script type=\"text/javascript\" src=\"http://news.sohu.com/upload/article/2012/js/pubPicTextV6_20130926.js\"></script>\n" +
                "\t\t\t<script type=\"text/javascript\">\n" +
                "\t\t\t\tvar newsId = 473732465;\n" +
                "\t\t\t\tjQuery.picText.init();\n" +
                "\t\t\t</script>\n" +
                "\t\t\t<!--图文转载 end-->\n" +
                "\t\t\t<!-- 广告 -->\n" +
                "\t\t\t<div class=\"ad590\">\n" +
                "\t\t\t\t<div><SOHUADCODE><div>\n" +
                "<script charset=\"gbk\" src=\"http://p.tanx.com/ex?i=mm_17187609_2273741_13022651\"></script>\n" +
                "    <script language=javascript>\n" +
                "        require([\"sjs/matrix/ad/passion\"], function(passion){\n" +
                "         passion.report(\"pv\", {adsrc:6,adid:'20q1E0q2h0000000q73000qyx',monitorkey:'20q1E0q2h0000000q73000qyx',cont_id :'beans_12022'}); \n" +
                "});\n" +
                "</script>\n" +
                "<script type='text/javascript'>\n" +
                "function addImp(adobj){\n" +
                "\t\t\t\tvar img = new Image();\n" +
                "\t\t\t\timg.onload = function(){\n" +
                "\t\t\t\t\timg = null;\n" +
                "\t\t\t\t};\n" +
                "\t\t\t\timg.src = adobj;\n" +
                "\t\t\t}\n" +
                "addImp(\"http://imp.optaim.com/201507/9c21da63769d55542c77e7d98c9c4531.php?a=0\");\n" +
                "</script></div>\n" +
                "</SOHUADCODE></div>\n" +
                "\t\t\t</div>\n" +
                "\n" +
                "                                <SOHUADCODE></SOHUADCODE>\n" +
                "\n" +
                "\t\t\t<!-- 20130121 st -->\n" +
                "\t\t\t<div class=\"lottery-box clear\" id=\"sougocaipiao1\"><a href=\"http://caipiao.sohu.com\" target=\"_blank\" rel=\"nofollow\">体彩强化品牌公益形象</a>&nbsp;&nbsp;<a href=\"http://caipiao.sohu.com\" target=\"_blank\" rel=\"nofollow\">双色球136期：头奖8注756万</a>&nbsp;&nbsp;<a  href=\"http://caipiao.sohu.com\" target=\"_blank\" rel=\"nofollow\">中头奖创业资金有着落</a>&nbsp;&nbsp;<a href=\"http://caipiao.sohu.com/video/tycpkj/index.shtml\" target=\"_blank\" rel=\"nofollow\">体彩摇奖</a></div\t\n" +
                "\t\t\t<!-- 20130121 end -->\n" +
                "\t\t\t<div class=\"mutu clear\">\n" +
                "\t\t\t\t<div class=\"mutu-title\">\n" +
                "\t\t\t\t\t<div class=\"mutu-news-tt\">\n" +
                "                   <h2><span>相关新闻</span></h2>\n" +
                "                    </div>\n" +
                "\t\t\t\t\t<div class=\"mutu-keyWord-tt\">\n" +
                "                   <h2><span>相关推荐</span></h2>\n" +
                "                    </div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"mutu-news\">\n" +
                "\t\t\t\t\t\t<div class=\"list14\">\n" +
                "\t\t\t\t\t\t\t\n" +
                "\n" +
                "\t\t\t <ul> \t\t<li><span>16-11-17</span><a href=\"http://news.sohu.com/20161117/n473389442.shtml\" onclick=\"return sogouRelateNews(this);\" len=\"6\"  ind=\"1\" target=\"_blank\">军媒调查教官踢裂男生睾丸:军训亟待依法规范</a></li>\n" +
                "\t\t\t\t\t\t\t\t<li><span>16-10-28</span><a href=\"http://news.sohu.com/20161028/n471604729.shtml\" onclick=\"return sogouRelateNews(this);\" len=\"6\"  ind=\"2\" target=\"_blank\">国防部答“中国南海军演反击美国”:例行训练</a></li>\n" +
                "\t\t\t\t\t\t\t\t<li><span>16-10-15</span><a href=\"http://news.sohu.com/20161015/n470313875.shtml\" onclick=\"return sogouRelateNews(this);\" len=\"6\"  ind=\"3\" target=\"_blank\">戒除网瘾学校现状:普遍收费高 以军事训练为主</a></li>\n" +
                "\t\t\t\t\t\t\t\t<li><span>16-09-26</span><a href=\"http://news.sohu.com/20160926/n469224173.shtml\" onclick=\"return sogouRelateNews(this);\" len=\"6\"  ind=\"4\" target=\"_blank\">军委纪委培训 10位进京书记里你认识几个?(图)</a></li>\n" +
                "\t\t\t\t\t\t\t\t<li><span>16-08-05</span><a href=\"http://news.sohu.com/20160805/n462807465.shtml\" onclick=\"return sogouRelateNews(this);\" len=\"6\"  ind=\"5\" target=\"_blank\">军队招生职能调整至军委训管部 今年首次组织招生</a></li>\n" +
                "\t\t\t\t\t\t\t\t<li><span>16-07-27</span><a href=\"http://news.sohu.com/20160727/n461298986.shtml\" onclick=\"return sogouRelateNews(this);\" len=\"6\"  ind=\"6\" target=\"_blank\">田晓蔚已调任军委训练管理部政治工作局主任</a></li>\n" +
                "\t\t </ul> \t\n" +
                "\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<div class=\"more\"><a href=\"http://news.sogou.com/news?query=实战 军委&pid=31023101&md=listTopics&name=&mode=0&sort=0\" rel=\"nofollow\" target=\"_blank\" >更多关于 <strong>实战 军委</strong> 的新闻&gt;&gt;</a> </div>\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"mutu-keyWord\">\n" +
                "\t\t\t\t\t\t<div class=\"list14\">\n" +
                "\t\t\t\t\t\t\t<ul>\n" +
                "\t<li><a href=\"http://www.sogou.com/web?query=%E9%83%A8%E9%98%9F%E9%99%A2%E6%A0%A1%E5%86%9B%E4%BA%8B%E6%8A%80%E8%83%BD%E6%AF%94%E6%AD%A6&ie=utf8&p=31210100&fhintidx=0\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" len=\"6\" ind=\"1\">部队院校军事技能比武</a></li>\n" +
                "\t<li><a href=\"http://www.sogou.com/web?query=%E5%86%9B%E4%BA%8B%E6%8A%80%E8%83%BD%E8%AE%AD%E7%BB%83%E6%95%99%E7%A8%8B&ie=utf8&p=31210100&fhintidx=1\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" len=\"6\" ind=\"2\">军事技能训练教程</a></li>\n" +
                "\t<li><a href=\"http://www.sogou.com/web?query=%E6%B0%91%E5%85%B5%E5%86%9B%E4%BA%8B%E8%AE%AD%E7%BB%83%E6%80%BB%E7%BB%93&ie=utf8&p=31210100&fhintidx=2\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" len=\"6\" ind=\"3\">民兵军事训练总结</a></li>\n" +
                "\t<li><a href=\"http://www.sogou.com/web?query=%E4%B8%AD%E5%A4%AE%E5%86%9B%E5%A7%94%E8%AE%AD%E7%BB%83%E7%AE%A1%E7%90%86%E9%83%A8&ie=utf8&p=31210100&fhintidx=3\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" len=\"6\" ind=\"4\">中央军委训练管理部</a></li>\n" +
                "\t<li><a href=\"http://www.sogou.com/web?query=%E5%B0%94%E9%9B%85%E5%86%9B%E4%BA%8B%E7%90%86%E8%AE%BA%E7%AD%94%E6%A1%882016&ie=utf8&p=31210100&fhintidx=4\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" len=\"6\" ind=\"5\">尔雅军事理论答案2016</a></li>\n" +
                "\t<li><a href=\"http://www.sogou.com/web?query=%E5%86%9B%E4%BA%8B%E5%AE%9E%E6%88%98%E6%BC%94%E4%B9%A0%E8%A7%86%E9%A2%91&ie=utf8&p=31210100&fhintidx=5\" onclick=\"return sogouRelateWeb(this);\" target=\"_blank\" len=\"6\" ind=\"6\">军事实战演习视频</a></li>\n" +
                "<script >\n" +
                "try{    \n" +
                "        var i=new Image();\n" +
                "        i.src='http://pingback.sogou.com/rltpv.png?surl='+escape(window.location)+'&'+Math.random();\n" +
                "}catch(err){\n" +
                "}       \n" +
                "</script></ul>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\n" +
                "\t\t\t<!-- 搜狗服务 -->\n" +
                "\t\t\t<div class=\"sogouService clear\">\n" +
                "            \t<h2><span><a href=\"http://www.sogou.com/\" target=\"_blank\"><img src=\"http://news.sohu.com/upload/pagerevision20090916/sogou.png\" width=\"74\" height=\"22\" alt=\"\" /></a></span><a href=\"http://www.sogou.com/fuwu/\" rel=\"nofollow\" target=\"_blank\"><strong>我要发布</strong></a></h2>\n" +
                "                <script type=\"text/javascript\">\n" +
                "\t                var sogou_ad_id=13273;\n" +
                "\t                var sogou_ad_height=90;\n" +
                "\t                var sogou_ad_width=580;\n" +
                "                </script>\n" +
                "                <script language='JavaScript' type='text/javascript' src='http://images.sohu.com/cs/jsfile/js/c.js'></script>\n" +
                "            </div>\n" +
                "\t\t\t\n" +
                "\t\t\t<!-- 千帆直播 -->\n" +
                "\t\t\t<style>\n" +
                "\t\t\t.qf-video{margin:30px auto 0;}\n" +
                "\t\t\t</style>\n" +
                "\t\t\t<div class=\"qf-video clear\"><iframe width=\"650\" height=\"180\" marginwidth=\"0\" marginheight=\"0\" hspace=\"0\" vspace=\"0\" frameborder=\"0\" scrolling=\"no\" src=\"http://sp.qf.56.com/sohu/protal.html\"></iframe></div>\n" +
                "\t\t</div>\n" +
                "\t</div>\t\n" +
                "\t<div class=\"rightbar-wrapper grid-300\">\n" +
                "\t\t<!-- 搜索 热词 -->\n" +
                "\t\t<div class=\"search-hotword clear\">\n" +
                "\t\t\t<div class=\"search\">\n" +
                "\t\t\t\t<form action=\"http://www.sogou.com/web\" method=\"get\" target=\"_blank\" id=\"searchform\" name=\"searchform\" onsubmit=\"return CheckKeyWord();\" autocomplete=\"off\"> \n" +
                "\t\t\t\t\t<input type=\"text\" name=\"query\" id=\"query\" class=\"search-in\" value=\"实战 军委\" />\n" +
                "\t\t\t\t\t<input type=\"hidden\" name=\"t\" id=\"t\" value=\"news\" />\n" +
                "\t\t\t\t\t<input type=\"submit\" class=\"search-btn\" value=\"搜狗搜索\" onmouseover=\"this.className='search-btn search-btn-over'\" onmouseout=\"this.className='search-btn'\" />\n" +
                "\t\t\t\t</form>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"hotword\">\n" +
                "\t\t\t\t<p>热词：\n" +
                "<a href=\"http://www.sogou.com/sogou?pid=sogou-inse-c5ab6cebaca97f71&query=%E5%91%A8%E6%B6%9B%E8%B7%B3%E6%A7%BD\" target=\"_blank\" rel=\"nofollow\">周涛跳槽</a>\n" +
                "<a href=\"http://www.sogou.com/sogou?pid=sogou-inse-c5ab6cebaca97f71&query=%E6%9C%80%E5%8F%AF%E6%80%95%E6%B3%B3%E6%B1%A0\" target=\"_blank\" rel=\"nofollow\">最可怕泳池</a>\n" +
                "<a href=\"http://www.sogou.com/sogou?pid=sogou-inse-c5ab6cebaca97f71&query=%E4%B8%B4%E6%97%B6%E6%83%85%E4%BE%A3\" target=\"_blank\" rel=\"nofollow\">临时情侣</a>\n" +
                "<a href=\"http://www.sogou.com/sogou?pid=sogou-inse-c5ab6cebaca97f71&query=%E6%9C%A8%E4%B9%83%E4%BC%8A%E9%B3%84%E9%B1%BC\" target=\"_blank\" rel=\"nofollow\">木乃伊鳄鱼</a></p>\n" +
                "\t\t\t\t<p>热剧：<a href=\"http://tv.sohu.com/s2016/fyqm/\" target=\"_blank\">法医秦明</a> <a href=\"http://tv.sohu.com/s2016/dsjplh/\" target=\"_blank\">屏里狐</a> <a href=\"http://tv.sohu.com/s2016/xldsy/\" target=\"_blank\">心里的声音</a> <a href=\"http://tv.sohu.com/s2016/hypq2/\" target=\"_blank\">花样排球2</a> <a href=\"http://tv.sohu.com/s2016/mjhmd3/\" target=\"_blank\">黑名单3</a></p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "        <script type=\"text/javascript\">\n" +
                "        function CheckKeyWord(){\n" +
                "                 var sf = document.searchform;\n" +
                "                 with(sf){\n" +
                "          //if (query.value == \"实战 军委\") {\n" +
                "          //                query.value = \"\";\n" +
                "          //                setTimeout(\"query.value='实战 军委'\", 1000);\n" +
                "          //       }\n" +
                "        }\n" +
                "                 return true;\n" +
                "        }\n" +
                "        \n" +
                "        // 改变提交的查询动作\n" +
                "        function CheckFormAction(v){\n" +
                "                 var sf = document.searchform;\n" +
                "                 with(sf){\n" +
                "          if (v=='web') {\n" +
                "                          action = \"http://www.sogou.com/web\";\n" +
                "            p.value = \"02240100\";                                 \n" +
                "                 } else {\n" +
                "                          p.value = \"02240101\";\n" +
                "                          action = \"http://news.sogou.com/news\";\n" +
                "                 }\n" +
                "        }\n" +
                "                 return true;\n" +
                "        }\n" +
                "\n" +
                "        jQuery(function(jq){\n" +
                "                 var searchValue = jq(\".search-in\").val();\n" +
                "                 jq(\".search-in\").focus(function(){\n" +
                "                          jq(this).val(\"\").css(\"color\",\"#333\");\n" +
                "                          jq(\".search\").addClass(\"search-act\");\n" +
                "                 }).blur(function(){\n" +
                "                          if(jq(this).val() == \"\"){\n" +
                "                                    jq(this).val(searchValue).css(\"color\",\"#666\");\n" +
                "                                    jq(\".search\").removeClass(\"search-act\");\n" +
                "                          } \n" +
                "                 })\n" +
                "        });\n" +
                "        </script>\n" +
                "\t\t\n" +
                "\t\t<!-- 广告 -->\n" +
                "\t\t<SOHUADCODE><script type=\"text/javascript\" src=\"http://txt.go.sohu.com/ip/soip\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "if(typeof(document.pv)==\"undefined\") document.pv = new Array();var _a=new Image();\n" +
                "_a.src=\"http://imp.optaim.com/201408/227508674023a85b81418b2fd35d3b5a.php?a=1\";\n" +
                "document.pv.push(_a);\n" +
                "</script><script type=\"text/javascript\">\n" +
                "Passion.tclick=function(ctid){\n" +
                "    Passion.pv.imp(\"http://xls.go.sohu.com/201301/dec6fec990dffa2804915cbc46fbefee.php?a=\"+ctid);\n" +
                "}\n" +
                "</script><div class=\"ad300\">\n" +
                "<table width=300 height=250 border=0 cellpadding=0 cellspacing=0>\n" +
                "<tr>\n" +
                "<td width=300 height=250 align=middle>\n" +
                "<div id=TurnAD472 width=300 height=250>\n" +
                " <script type=text/javascript>\n" +
                "var TurnAD472=new Cookie(document,\"TurnAD472\",24);\n" +
                "TurnAD472.load();\n" +
                "TurnAD472.visit=(TurnAD472.visit==null)?parseInt(Math.random()*3+1):TurnAD472.visit;\n" +
                "if(TurnAD472.visit!=0)var intTurnAD472=TurnAD472.visit;\n" +
                "TurnAD472.visit++;\n" +
                "TurnAD472.visit=(TurnAD472.visit>3)?1:TurnAD472.visit;\n" +
                "TurnAD472.store();\n" +
                "function showTurnAD472(basenum){\n" +
                "if (basenum==1){\n" +
                "if (typeof has_topAd == \"undefined\" || has_topAd==0) {\n" +
                "//topnews代码，投放时请带上，轮换代码从下面开始替换\n" +
                "jQuery(\"#TurnAD472\").attr('id','tanx-a-mm_17187609_2273741_13026471');\n" +
                "document.write('<script charset=\"gbk\" src=\"http://p.tanx.com/ex?i=mm_17187609_2273741_13026471\"></sc'+'ript>');\n" +
                "require([\"sjs/matrix/ad/passion\"], function(passion){\n" +
                "         passion.report(\"pv\", {adsrc:6,adid:'20q1E0q2h0000000q73000qyx',monitorkey:'20q1E0q2h0000000q73000qyx',cont_id :'beans_11953'}); \n" +
                "});\n" +
                "//轮换代码结束\n" +
                "}\n" +
                "function addImp(adobj){\n" +
                "\t\t\t\tvar img = new Image();\n" +
                "\t\t\t\timg.onload = function(){\n" +
                "\t\t\t\t\timg = null;\n" +
                "\t\t\t\t};\n" +
                "\t\t\t\timg.src = adobj;\n" +
                "\t\t\t}\n" +
                "addImp(\"http://imp.optaim.com/201507/19b28503a51ae5fecca69243df588209.php?a=0\");}\n" +
                "else if(basenum==2){\n" +
                "if (typeof has_topAd == \"undefined\" || has_topAd==0) {\n" +
                "//topnews代码，投放时请带上，轮换代码从下面开始替换\n" +
                "require([\"sjs/matrix/ad/passion\"], function (passion) {\n" +
                "    var _C = \"#TurnAD472\",_ID = \"10108\",_W = 300,_H = 250,_T = 2,_F=201;\n" +
                "    if(_C){jQuery(_C).attr('id','beans_'+_ID).css({'width' : _W + 'px', 'height' : _H + 'px','margin' : '0 auto'});}\n" +
                "    passion.ones({itemspaceid : _ID,width:_W,height:_H,adsrc : _F,turn : _T,defbeans : !0});\n" +
                "    });\n" +
                "//轮换代码结束\n" +
                "}}\n" +
                "else{\n" +
                "if (typeof has_topAd == \"undefined\" || has_topAd==0) {\n" +
                "//topnews代码，投放时请带上，轮换代码从下面开始替换\n" +
                "require([\"sjs/matrix/ad/passion\"], function (passion) {\n" +
                "    var _C = \"#TurnAD472\",_ID = \"10108\",_W = 300,_H = 250,_T = 3,_F=201;\n" +
                "    if(_C){jQuery(_C).attr('id','beans_'+_ID).css({'width' : _W + 'px', 'height' : _H + 'px','margin' : '0 auto'});}\n" +
                "    passion.ones({itemspaceid : _ID,width:_W,height:_H,adsrc : _F,turn : _T,defbeans : !0});\n" +
                "    });\n" +
                "//轮换代码结束\n" +
                "}}\n" +
                "}\n" +
                "showTurnAD472(intTurnAD472);\n" +
                "</script>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "<script>var divid300500 = 'TurnAD472';</script>\n" +
                "<script type=\"text/javascript\" src=\"http://images.sohu.com/bill/tongyong/js/top/Topnewsthird300500V20140925.js\" ></script></SOHUADCODE>\t\t\n" +
                "\t\t<!-- 手机看新闻 -->\n" +
                "\t\t<script src=\"http://a1.itc.cn/pv/js/pvcode.1312101227.js\"></script>\n" +
                "<div class=\"phone_rss\">\n" +
                "   <a href=\"http://k.sohu.com\" onclick=\"_pv.send('5af69b5503b22dd5')\" target=\"_blank\"><img src=\"http://i0.itc.cn/20160913/a75_6b415468_0160_9d9d_2462_6621521fdc30_1.jpg\" width=\"300\" height=\"92\"></a></div>\n" +
                "<div class=\"blank5B\"></div>\t\t\n" +
                "\t\t<!-- 相关微博 -->\n" +
                "\t\t\n" +
                "\t\t<div class=\"news-frag clear\">\n" +
                "\t\t <h2><span>数字之道</span></h2>\n" +
                "\t\t <div class=\"clk-today clear\">\n" +
                "\n" +
                "  <div class=\"pic-text\">\n" +
                "    <div><a target=\"_blank\" href=\"http://news.sohu.com/s2016/shuzi-492/index.shtml\"><img src=\"http://i2.itc.cn/20161111/35e6_c00884d7_ee5e_18a7_ebd4_5e7637a072d9_1.gif\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\" height=\"75\" border=\"0\"></a></div>\n" +
                "    <h4><a target=\"_blank\" href=\"http://news.sohu.com/s2016/shuzi-492/index.shtml\">\n" +
                "大学生染艾暴增 三成感染者未察觉\n" +
                "\n" +
                "</a></h4>\n" +
                "<p>高校艾滋病男男传播占8成。[<a target=\"_blank\" href=\"http://news.sohu.com/s2016/shuzi-492/index.shtml\">详细</a>]</p>\n" +
                "  </div>\n" +
                "  <div class=\"list12\">\n" +
                "    <ul>\n" +
                "      <li><span>神吐槽：</span><a href=\"http://news.sohu.com/20161121/n473735206.shtml\" target=\"_blank\">中性霾应该怎么吸才显得有逼格</a></li>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </ul>\n" +
                "  </div>\n" +
                "</div>\n" +
                "<div class=\"rightbar-foot\" style=\"margin-top:10px;\"></div>\n" +
                "\t\t</div>\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\n" +
                "\t\t<!-- 广告 -->\n" +
                "\t\t<SOHUADCODE><div class=\"ad300\">\n" +
                "<table width=300 height=250 border=0 cellpadding=0 cellspacing=0>\n" +
                "<tr>\n" +
                "<td width=300 height=250 align=middle>\n" +
                "<div id=TurnAD473 width=300 height=250>\n" +
                " <script type=text/javascript>\n" +

                "<li class=\"end\"><a href=\"http://tv.sohu.com/20161121/n473690571.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i0.itc.cn/20161121/330b_0742d76d_017f_2c6b_e499_9148d8e0d3d2_1.jpg\" width=\"134\" height=\"86\" alt=\"缅北多地发生武装冲突\" /><span>缅北多地发生武装冲突</span><em ></em></a></li>\n" +
                "\n" +
                "<li><a href=\"http://tv.sohu.com/20161121/n473690792.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i2.itc.cn/20161121/330b_59689d25_97fc_be54_d4ce_9df471b795c6_1.jpg\" width=\"134\" height=\"86\" alt=\"奔驰中国高管公然辱华\" /><span>奔驰中国高管公然辱华</span><em ></em></a></li>\n" +
                "\n" +
                "<li class=\"end\"><a href=\"http://tv.sohu.com/20161120/n473668525.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i3.itc.cn/20161121/330b_c022cc8d_0e22_0929_2faf_05a2b8f73bc6_1.jpg\" width=\"134\" height=\"86\" alt=\"怀孕7月护士跪地抢救路人\" /><span>怀孕护士跪地抢救路人</span><em ></em></a></li>\n" +
                "\n" +
                "<li><a href=\"http://tv.sohu.com/20161120/n473658488.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i3.itc.cn/20161121/330b_1bde76d2_65c9_501d_6e22_0e2c4fa8b5ae_1.jpg\" width=\"134\" height=\"86\" alt=\"男子扮“齐天大圣”想走红\" /><span>男子扮大圣饭店骚扰食客</span><em ></em></a></li>\n" +
                "\n" +
                "<li class=\"end\"><a href=\"http://tv.sohu.com/20161120/n473648519.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i2.itc.cn/20161121/330b_21643a4b_0423_1fd4_0605_732f1bd8b34c_1.jpg\" width=\"134\" height=\"86\" alt=\"博美的百年妆容演变\"/><span>博美的百年妆容演变</span><em ></em></a></li>\n" +
                "\n" +
                "<li><a href=\"http://tv.sohu.com/20140101/n392764201.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i2.itc.cn/20161121/330b_967d40bf_017b_42e7_e9be_2945e8aba4bf_1.jpg\" width=\"134\" height=\"86\" alt=\"我是你的眼\" /><span>你的眼-严真真走私器械</span><em ></em></a></li> \n" +
                "\n" +
                "<li class=\"end\"><a href=\"http://tv.sohu.com/20161103/n472228939.shtml?txid=f2ed2da12b7aaef378cc10866b4f4e79\" target=\"_blank\"><img src=\"http://i3.itc.cn/20161121/330b_891777f8_0195_4aec_6a01_d18146298b69_1.jpg\" width=\"134\" height=\"86\" alt=\"法医秦明\" /><span>法医-林队撮合大宝秦明</span><em ></em></a></li>\n" +
                "\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"rightbar-foot\"></div>\n" +
                "\t\t<!-- 广告 -->\n" +
                "\t\t<SOHUADCODE><div class=\"ad300\">\n" +
                "<table width=300 height=250 border=0 cellpadding=0 cellspacing=0>\n" +
                "<tr>\n" +
                "<td width=300 height=250 align=middle>\n" +
                "<div id=TurnAD474 width=300 height=250>\n" +
                " <script type=text/javascript>\n" +
                "var TurnAD474=new Cookie(document,\"TurnAD474\",24);\n" +
                "TurnAD474.load();\n" +
                "TurnAD474.visit=(TurnAD474.visit==null)?parseInt(Math.random()*2+1):TurnAD474.visit;\n" +
                "if(TurnAD474.visit!=0)var intTurnAD474=TurnAD474.visit;\n" +
                "TurnAD474.visit++;\n" +
                "TurnAD474.visit=(TurnAD474.visit>2)?1:TurnAD474.visit;\n" +
                "TurnAD474.store();\n" +
                "function showTurnAD474(basenum){\n" +
                "if (basenum==1){\n" +
                "document.write('<a style=\"display:none!important\" id=\"tanx-a-mm_17187609_2273741_14706573\"></a>');\n" +
                "     tanx_s = document.createElement(\"script\");\n" +
                "     tanx_s.type = \"text/javascript\";\n" +
                "     tanx_s.charset = \"gbk\";\n" +
                "     tanx_s.id = \"tanx-s-mm_17187609_2273741_14706573\";\n" +
                "     tanx_s.async = true;\n" +
                "     tanx_s.src = \"http://p.tanx.com/ex?i=mm_17187609_2273741_14706573\";\n" +
                "     tanx_h = document.getElementsByTagName(\"head\")[0];\n" +
                "     if(tanx_h)tanx_h.insertBefore(tanx_s,tanx_h.firstChild);\n" +
                "function addImp(adobj){\n" +
                "\t\t\t\tvar img = new Image();\n" +
                "\t\t\t\timg.onload = function(){\n" +
                "\t\t\t\t\timg = null;\n" +
                "\t\t\t\t};\n" +
                "\t\t\t\timg.src = adobj;\n" +
                "\t\t\t}\n" +
                "addImp(\"http://imp.optaim.com/201507/2335dd35a7246956fea8dfb9055eeed8.php?a=0\");}\n" +
                "else{\n" +
                "document.write('<a style=\"display:none!important\" id=\"tanx-a-mm_17187609_2273741_14706573\"></a>');\n" +
                "     tanx_s = document.createElement(\"script\");\n" +
                "     tanx_s.type = \"text/javascript\";\n" +
                "     tanx_s.charset = \"gbk\";\n" +
                "     tanx_s.id = \"tanx-s-mm_17187609_2273741_14706573\";\n" +
                "     tanx_s.async = true;\n" +
                "     tanx_s.src = \"http://p.tanx.com/ex?i=mm_17187609_2273741_14706573\";\n" +
                "     tanx_h = document.getElementsByTagName(\"head\")[0];\n" +
                "     if(tanx_h)tanx_h.insertBefore(tanx_s,tanx_h.firstChild);\n" +
                "function addImp(adobj){\n" +
                "\t\t\t\tvar img = new Image();\n" +
                "\t\t\t\timg.onload = function(){\n" +
                "\t\t\t\t\timg = null;\n" +
                "\t\t\t\t};\n" +
                "\t\t\t\timg.src = adobj;\n" +
                "\t\t\t}\n" +
                "addImp(\"http://imp.optaim.com/201507/2335dd35a7246956fea8dfb9055eeed8.php?a=0\");}\n" +
                "}\n" +
                "showTurnAD474(intTurnAD474);\n" +
                "</script>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</SOHUADCODE>\n" +
                "\t\t<!-- 中视网盟 -->\n" +
                "\t\t<SOHUADCODE><div>\n" +
                "<div id=beans_14174 width=300 height=250 align=\"center\"></div>\n" +
                "<script type=\"text/javascript\">\n" +
                "require([\"sjs/matrix/ad/passion\"], function (passion) {\n" +
                "    var _ID = \"14174\",_W = 300,_H = 250,_T =1,_F=201;\n" +
                "    if(_ID){jQuery('#beans_'+_ID).css({'width' : _W + 'px', 'height' : _H + 'px','margin' : '0 auto'});}\n" +
                "    passion.ones({itemspaceid : _ID,width:_W,height:_H,adsrc : _F,turn : _T,defbeans : !0});\n" +
                "    });\n" +
                "</script>\n" +
                "</div>\n" +
                "</SOHUADCODE>\n" +
                "\t\t<!-- 彩票 st -->\n" +
                "\t\t\n" +
                "\t\t<!-- 彩票 end -->\t\t\n" +
                "\t\t<!-- 我来说两句排行榜 -->\n" +
                "\t\t\n" +
                "\n" +
                "<div class=\"comment-rank clear\">\n" +
                "    <h2>\n" +
                "        <span>我来说两句排行榜</span>\n" +
                "    </h2>\n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473679966.html\" target=\"_blank\">2491</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161120/n473679966.shtml\" target=\"_blank\">网曝奔驰中国高管辱华向路人喷辣椒水 北京警方回应&lt;-搜狐新闻</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473679966.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473684172.html\" target=\"_blank\">1549</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161121/n473684172.shtml\" target=\"_blank\">台湾女留学生不满被列中国籍 结果被改“无国籍”</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473684172.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473677328.html\" target=\"_blank\">1386</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161120/n473677328.shtml\" target=\"_blank\">网曝奔驰中国高管辱华 路人上前理论被喷辣椒水</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473677328.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473647543.html\" target=\"_blank\">1706</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://learning.sohu.com/20161120/n473647543.shtml\" target=\"_blank\">“滚回中国去！” 美国种族歧视的烈火已烧到华人和留学生身上</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473647543.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473697330.html\" target=\"_blank\">679</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://auto.sohu.com/20161121/n473697330.shtml\" target=\"_blank\">戴姆勒回应外籍高管辱华：深表遗憾 正配合调查-搜狐汽车</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473697330.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473684169.html\" target=\"_blank\">610</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161121/n473684169.shtml\" target=\"_blank\">党员林丹出轨 是否涉嫌严重违纪呢？-搜狐新闻</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473684169.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473688184.html\" target=\"_blank\">63</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161121/n473688184.shtml\" target=\"_blank\">中纪委：周薄郭徐令等案警示现在并非“天下太平”</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473688184.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473684138.html\" target=\"_blank\">351</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161121/n473684138.shtml\" target=\"_blank\">取保候审女嫌犯到各地旅游 晒旅游照被多人举报-搜狐新闻</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473684138.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473684408.html\" target=\"_blank\">328</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161121/n473684408.shtml\" target=\"_blank\">广东六厅官被移送司法 一人19次因私出国不报告-搜狐新闻</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473684408.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    <div class=\"broken-line\"></div>\n" +
                "    \n" +
                "    \n" +
                "    <dl>\n" +
                "        <dt>\n" +
                "            <a href=\"http://pinglun.sohu.com/s473657715.html\" target=\"_blank\">749</a>\n" +
                "        </dt>\n" +
                "        <dd>\n" +
                "            <a href=\"http://news.sohu.com/20161120/n473657715.shtml\" target=\"_blank\">网传医生处方字迹工整 被评为“医生界的清流”</a>\n" +
                "            [<em class=\"red\"><a href=\"http://pinglun.sohu.com/s473657715.html\" target=\"_blank\">评</a></em>]\n" +
                "        </dd>\n" +
                "    </dl>\n" +
                "    \n" +
                "    \n" +
                "</div>\n" +
                "\t\t<div class=\"rightbar-foot\"></div>\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t<!-- 广告 -->\n" +
                "\t\t<SOHUADCODE><div class=\"ad300\">\n" +
                "<div>\n" +
                "<div id=beans_13568 width=300 height=90 align=\"center\"></div>\n" +
                "<script type=\"text/javascript\">\n" +
                "require([\"sjs/matrix/ad/passion\"], function (passion) {\n" +
                "    var _ID = \"13568\",_W = 300,_H = 90,_T =1,_F=201;\n" +
                "    if(_ID){jQuery('#beans_'+_ID).css({'width' : _W + 'px', 'height' : _H + 'px','margin' : '0 auto'});}\n" +
                "    passion.ones({itemspaceid : _ID,width:_W,height:_H,adsrc : _F,turn : _T,defbeans : !0});\n" +
                "    });\n" +
                "</script></div>\n" +
                "</div>\n" +
                "</SOHUADCODE>\t\t\n" +
                "\t\t<div class=\"rightbar-head\"></div>\t\t\t\t\n" +
                "\t\t<!-- 信息 -->\n" +
                "\t\t<div class=\"sohu-info clear\" >\n" +
                "\t\t\t<div class=\"contact-icon\"></div>\n" +
                "\t\t\t<div class=\"contact-txt\">\n" +
                "\t\t\t\t<p>客服热线：86-10-58511234</p>\n" +
                "\t\t\t\t<p>客服邮箱：<a href=\"mailto:kf@vip.sohu.com\" target=\"_blank\">kf@vip.sohu.com</a></p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\t\t\n" +
                "\t</div>\n" +
                "\t<div class=\"content-footer\"></div>\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"http://stock.sohu.com/upload/stock/business_news_1.1.3.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://stock.sohu.com/upload/stock/related_stock_1.0.1.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://getip.js.sohu.com/ip/\"></script>\n" +
                "<script type=\"text/javascript\"> \n" +
                "if (new_ip == null) var new_ip=2130706433;\n" +
                "var dns_requrl=\"\\<script type=\\\"text/javascript\\\" src=\\\"http://\" + new_ip +\".sohuns.com/ip/\\\"\\>\\</script\\>\";\n" +
                "document.write(dns_requrl);\n" +
                "</script>\n" +
                "\n" +
                "<!-- 标准尾 start -->\n" +
                "<div collection=\"Y\">\n" +
                "<div class=\"clear\"></div>\n" +
                "\t<div id=\"foot\" class=\"Area area\">\n" +
                "\t\t<a href=\"javascript:void(0)\" onClick=this.style.behavior='url(#default#homepage)';this.setHomePage('http://www.sohu.com');return false;>设置首页</a>\n" +
                "\t\t- <a href=http://pinyin.sogou.com/ target=_blank rel=\"nofollow\">搜狗输入法</a>\n" +
                "\t\t- <a href=http://pay.sohu.com/ target=_blank rel=\"nofollow\">支付中心</a>\n" +
                "\t\t- <a href=http://hr.sohu.com target=_blank rel=\"nofollow\">搜狐招聘</a>\n" +
                "\t\t- <a href=http://ad.sohu.com/ target=_blank rel=\"nofollow\">广告服务</a>\n" +
                "\t\t- <a href=http://sohucallcenter.blog.sohu.com/ target=_blan rel=\"nofollow\"k>客服中心</a>\n" +
                "\t\t- <a href=http://corp.sohu.com/s2006/contactus/ target=_blank rel=\"nofollow\">联系方式</a>\n" +
                "\t\t- <a href=http://www.sohu.com/about/privacy.html target=_blank rel=\"nofollow\">保护隐私权</a>\n" +
                "\t\t- <a href=http://investors.sohu.com/ target=_blank rel=\"nofollow\">About SOHU</a>\n" +
                "\t\t- <a href=http://corp.sohu.com/indexcn.shtml target=_blank rel=\"nofollow\">公司介绍</a>\n" +
                "\t\t- <a href=http://sitemap.sohu.com/ target=_blank>网站地图</a>\n" +
                "\t\t- <a href=http://roll.sohu.com/ target=_blank>全部新闻</a>\n" +
                "\t\t- <a href=http://blog.sohu.com/roll target=_blank>全部博文</a>\n" +
                "\t\t<br />Copyright <span class=\"fontArial\">&copy;</span> 2016 Sohu.com Inc. All Rights Reserved. 搜狐公司 <span class=\"unline\"><a href=\"http://corp.sohu.com/s2007/copyright/\" target=\"_blank\" rel=\"nofollow\">版权所有</a></span>\n" +
                "\t\t<br />搜狐不良信息举报邮箱：<a href=\"mailto:jubao@contact.sohu.com\">jubao@contact.sohu.com</a>\n" +
                "\t</div>\n" +
                "\t<SCRIPT language=JavaScript src=\"http://a1.itc.cn/pv/js/spv.1309051632.js\"></SCRIPT>\n" +
                "\t<script language=\"javascript\" src=\"http://js.sohu.com/wrating20120726.js\"></script>\n" +
                "\t<script language=\"javascript\"> \n" +
                "\t\tvar  _wratingId = null;\n" +
                "\t\ttry{\n" +
                "\t\t_wratingId = _getAcc();\n" +
                "\t\t}catch(e){}\n" +
                "\t\tif(_wratingId !=null){\n" +
                "\t\tdocument.write('<scr'+'ipt type=\"text/javascript\" src=\"http://sohu.wrating.com/a1.js\"></scr'+'ipt>');\n" +
                "\t\t}\n" +
                "\t\t</script>\n" +
                "\t\t<script language=\"javascript\"> \n" +
                "\t\tif(_wratingId !=null){\n" +
                "\t\tdocument.write('<scr'+'ipt type=\"text/javascript\">');\n" +
                "\t\tdocument.write('var vjAcc=\"'+_wratingId+'\";');\n" +
                "\t\tdocument.write('var wrUrl=\"http://sohu.wrating.com/\";');\n" +
                "\t\tdocument.write('try{vjTrack();}catch(e){}');\n" +
                "\t\tdocument.write('</scr'+'ipt>');\n" +
                "\t\t}\n" +
                "\t</script>\n" +
                "\t<!--SOHU:DIV_FOOT-->\n" +
                "</div>\n" +
                "<!-- 标准尾 end -->\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "<!--\n" +
                "\tfunction sogouRelateNews(itm){\n" +
                "\t\tvar i=new Image();\n" +
                "        var len=itm.getAttribute(\"len\");   var  ind= itm.getAttribute(\"ind\");\n" +
                "        len=(len==null)?\"\":len;   ind=(ind==null)?\"\":ind;\n" +
                "        if(len == \"\" || ind == \"\" ) {\n" +
                "           i.src='http://pingback.sogou.com/rltnews.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&'+Math.random();\n" +
                "        } else {\n" +
                "           i.src='http://pingback.sogou.com/rltnews.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&len='+len+'&ind='+ind+'&'+Math.random();\n" +
                "        }\n" +
                "\t    return true;\n" +
                "\t}\n" +
                "\n" +
                "\tfunction sogouRelateWeb(itm){\n" +
                "\t\tvar i=new Image();\n" +
                "        var len=itm.getAttribute(\"len\");  var  ind= itm.getAttribute(\"ind\");\n" +
                "        len=(len==null)?\"\":len; ind=(ind==null)?\"\":ind;\n" +
                "        if(len == \"\" || ind == \"\" ) {\n" +
                "\t \t     i.src='http://pingback.sogou.com/rltweb.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&'+Math.random();\n" +
                "        } else {\n" +
                "\t\t     i.src='http://pingback.sogou.com/rltweb.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&len='+len+'&ind='+ind+'&'+Math.random();\n" +
                "        }\n" +
                "\t\treturn true;\n" +
                "\t}\n" +
                "\n" +
                "\tfunction sogouRelateSaybar(itm){\n" +
                "\t\tvar i=new Image();\n" +
                "\t\ti.src='http://pingback.sogou.com/rltsb.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&'+Math.random();\n" +
                "\t\treturn true;\n" +
                "\t}\n" +
                "\n" +
                "\tfunction sogouRelateBlog(itm){\n" +
                "\t\tvar i=new Image();\n" +
                "\t\ti.src='http://pingback.sogou.com/rltblog.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&'+Math.random();\n" +
                "\t\treturn true;\n" +
                "\t}\n" +
                "\t\n" +
                "\tfunction sogouHotSaybar(itm){\n" +
                "\t\tvar i=new Image();\n" +
                "\t\ti.src='http://pingback.sogou.com/rlthotsb.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&'+Math.random();\n" +
                "\t\treturn true;\n" +
                "\t}\n" +
                "\n" +
                "    function sogouRelateVideoNews(itm){\n" +
                "      var i=new Image();\n" +
                "      var len=itm.getAttribute(\"len\");  var  ind= itm.getAttribute(\"ind\");\n" +
                "      len=(len==null)?\"\":len;    ind=(ind==null)?\"\":ind;\n" +
                "      if(len == \"\" || ind == \"\" ) {\n" +
                "        i.src='http://pingback.sogou.com/rltvideonews.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&'+Math.random();\n" +
                "      } else {\n" +
                "        i.src='http://pingback.sogou.com/rltvideonews.png?durl='+escape(itm.href)+'&surl='+escape(window.location)+'&len='+len+'&ind='+ind+'&'+Math.random();\n" +
                "      }\n" +
                "\t  return true;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "//-->\n" +
                "</script>\n" +
                "<SOHUADCODE>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<Script language=\"Javascript\">var cWidth=980;</script>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "\trequire([\"sjs/matrix/ad/special\"], function(special) {\n" +
                "\tspecial.ones({\n" +
                "\t\titemspaceid : 10632,\n" +
                "\t\tadsrc : 201,\n" +
                "\t\tmax_turn : 1,\n" +
                "\t\torder : 3,\n" +
                "\t\toptions:{\n" +
                "\t\t\tpos : \"left\"\n" +
                "\t\t}\n" +
                "\t});\n" +
                "//多媒体视窗\n" +
                "special.wait({\n" +
                "\titemspaceid :15026,\n" +
                "\tform : \"mediapop\",\n" +
                "\tadsrc : 200,\t\n" +
                "\tmax_turn : 2,\n" +
                "\torder : 5,\n" +
                "\tspec:{\n" +
                "\ttime_limit:2//两个轮换时此处写2 单投时此处写要求的次数限制\n" +
                "\t}\n" +
                "});\t \n" +
                "//摩天楼\n" +
                "special.wait({\n" +
                "  itemspaceid :15076,\n" +
                "  form:\"skyscraper\",\n" +
                "  adsrc : 200,\t\n" +
                "  max_turn : 2,\n" +
                "  order : 6,\n" +
                "  spec:{\n" +
                "\ttime_limit:2//两个轮换时此处写2 单投时此处写要求的次数限制\n" +
                "\t}\n" +
                "});\n" +
                "\n" +
                "\n" +
                "\t\n" +
                "special.start();\n" +
                "});</script>\n" +
                "\n" +
                "\n" +
                "<Script language=\"Javascript\" src=\"http://images.sohu.com/bill/s2013/PVJS/adm2012-20130926min.js\"></Script>\n" +
                "<script src='http://images.sohu.com/bill/s2010/gates/sohucstech/javascript/954092d923b0e377f0a9d4d41a0937c4.js' type='text/javascript'></script>\n" +
                "<!—不能删除 投飞行广告时也必须带上—>\n" +
                "<script type=\"text/javascript\">\n" +
                "if(typeof(document.pv)=='undefined') document.pv = new Array();var _a=new Image();\n" +
                "_a.src='http://xls.go.sohu.com/201012/ee970cf0f5c53c86014a16fd32d9629a.php?a=0';\n" +
                "document.pv.push(_a);\n" +
                "imp(urlpath,133);\n" +
                "if(PVUV_CheckArticle(\"gn11\",24,1)){imp(urlpath,134);}\n" +
                "if(PVUV_CheckArticle(\"gn12\",24*365,1)){imp(urlpath,135);}\n" +
                "\n" +
                "</script>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "if(typeof(document.pv)=='undefined') document.pv = new Array();var _a=new Image();\n" +
                "_a.src='http://xls.go.sohu.com/201204/ba6906fb71bb9c0a5d72babe338483b9.php?a=9';\n" +
                "document.pv.push(_a);\n" +
                "</script>\n" +
                "<!—不能删除—>\n" +
                "<!--<Script language=\"Javascript\">\n" +
                "AD = new ADM(\"FLOAT\", 2);\n" +
                "    AD.src = \"http://images.sohu.com/bill/s2009/shanbai/baojie/6yue/1001000602.swf\";\n" +
                "    AD.href=\"http://goto.sogou.com/200905/256711c20f526ea88028870e1a36e2cc.php\";\n" +
                "    AD.tag=\"\";\n" +
                "    AD.imp=\"\";\n" +
                "    AddSchedule(AD);\n" +
                "</script>-->\n" +
                "<script type=\"text/javascript\">SohuAd.createScript(\"sohucsjs\",\"http://imp.go.sohu.com/201111/ee4f7cebb2ef825bf6b3c8730c929929_31.js\");</script>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "SohuAdPv_CPD   = {\"#TurnAD472\":\"10108\",\"#TurnAD20\":\"10013\",\"#TurnAD473\":\"10110\",\"#TurnAD474\":\"10111\"};\n" +
                "SohuAdFly_nCPD = {\"float_r\":\"10632\"};\n" +
                "setpopslotid = '12409';\n" +
                "</script><Script language=\"Javascript\">if(jQuery){jQuery.getScript(\"http://images.sohu.com/bill/s2012/gates/all/map.2013040302.js\");}</script>\n" +
                "<Script language=\"Javascript\" src=\"http://images.sohu.com/bill/s2013/gates/js/fad_v9.js\"></Script>\n" +
                "<Script language=\"Javascript\" src=\"http://images.sohu.com/bill/default/auto/pop.1.4.js?t=168\"></Script></SOHUADCODE>\n" +
                "<SOHUADCODE></SOHUADCODE>\n" +
                "<script type='text/javascript'>\t\n" +
                "\tfunction q(s) {return s.replace(/%/g,\"%25\").replace(/&/g,\"%26\").replace(/#/g,\"%23\");}\t\n" +
                "\tvar sogou_is_brand_url = \"http://cpc.brand.sogou.com/brand_ad_new\";\t\n" +
                "\tvar brand_cnt = 0;\t\n" +
                "\tfor(var p in sogou_is_brand)\t\n" +
                "\t{\t           if(typeof(sogou_is_brand[p]) == \"string\")\n" +
                "\t               sogou_is_brand_url += (brand_cnt++ ? \"&\" : \"?\") + q(p) + \"=\" + q(sogou_is_brand[p]);\t\n" +
                "\t}\t\n" +
                "\tif (brand_cnt>3)\t\n" +
                "\t{\t\n" +
                "\t         var test= document.createElement('SCRIPT');\t\n" +
                "\t         test.src=sogou_is_brand_url;\t\n" +
                "\t         document.getElementsByTagName('head')[0].appendChild(test);\t\n" +
                "\t}\t\n" +
                "</script>\n" +
                "<div style=\"display:none\" id=\"sohu-dt0220mine-tag\">news</div><script src='http://news.sohu.com/upload/pop/mytag.min.js' type='text/javascript'></script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script>\n" +
                "pv_area_register(\"401832\",\"pvblock1\");\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>");
    }
}
