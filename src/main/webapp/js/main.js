
$(function(){
    $("#keyword").keyup(function(){
        getsuggest($("#keyword").val());
    });
    $("#submitBtn").click(function(){
        var keyword = $("#keyword").val();
        $.ajax({
            url : "/solr-system-final/action/search/",
            dataType : "json",
            contetType : "application/json",
            async : true,
            type : "POST",
            data : {keyword : keyword},

            success : function (data) {
                $("#searchResult").html("");
                var tmp = "";
                if (data.length == 0){
                    tmp += appendnoresult();
                }else {
                    data.forEach(function (item, index) {
                        tmp += appendResult(item);
                    })
                }
                $("#searchResult").html(tmp)
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    })

    function appendResult(resultData) {
        var htmlstr = "";
        htmlstr += "<div class='arctive'>";
        htmlstr += "<div class='votebar'><span class='votebar_icon'></span>支持</div>";
        htmlstr += "<div class='author'><b>"+resultData.recordType+"</b>描述<p>0个赞同</p></div>";
        htmlstr += "<div class='contentType'>"
        htmlstr += resultData.context;
        htmlstr += "</div></div>";
        return htmlstr;
    }

    function appendnoresult() {
        var htmlstr = "<div class='arctive'><div class='contentType'>暂无搜索结果</div></div>";
        return htmlstr;
    }

    function getsuggest(word) {
        $.ajax({
            url : "/solr-system-final/action/suggest/",
            dataType : "json",
            contetType : "application/json",
            async : true,
            type : "POST",
            data : {keyword : word},

            success : function (data) {
                $("#mylist").html("");
                console.log(data);
                var tmp = "";
                if (data.length != 0){
                    tmp += appendsuggest(data);
                }
                $("#mylist").html(tmp);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    function appendsuggest(data) {
        var htmlstr = "";
        data.forEach(function (item,index) {
            htmlstr += "<option>"+item+"</option>"
        })
        htmlstr += "<option>"+"</option>"
        return htmlstr;
    }
});