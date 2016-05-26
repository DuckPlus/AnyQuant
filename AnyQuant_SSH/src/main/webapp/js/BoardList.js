/**
 * Created by dsn on 2016/5/26.
 */
var globalData=[];
$.getJSON('/Board/getAllBoards', function (data) {
    globalData=data;
    initBubble(data);
});
//防止重复
function ifExist(index,num){
    for(var i=0;i<index.length;i++){
        if(index[i]==num){
            return 1;
        }
    }
    return 0;
}
function  initBubble(data) {
    var index=[],myData=[],length=globalData.length,i=0;
    while(i<10){
        var newNum=Math.floor(Math.random()*(length+1));
        if(ifExist(index,newNum)==0){
            index[i]=newNum;
            i++;
        }
    }
    for(var i=0;i<index.length;i++){
        myData[i]=({
            x: Math.random()*100,
            y: Math.random()*100,
            z: 25,
            name: globalData[index[i]]
        });
    }

    $('#bubble').highcharts({
        chart: {
            type: 'bubble',
            plotBorderWidth: 0,
            zoomType: 'xy'
        },
        tooltip: {
            useHTML: true,
            headerFormat: '<table>',
            pointFormat: '<tr><th colspan="2"><h3>{point.name}</h3></th></tr>'
            /*+'<tr><th>Fat intake:</th><td>{point.x}g</td></tr>' +
             '<tr><th>Sugar intake:</th><td>{point.y}g</td></tr>' +
             '<tr><th>Obesity (adults):</th><td>{point.z}%</td></tr>'*/
            ,

            footerFormat: '</table>',
            followPointer: true
        },
        legend: {
            enabled: false
        },

        title: {
            text: ''
        },

        xAxis: {
            visible:false
        },

        yAxis: {
            visible:false
        },

        plotOptions: {
            series: {
                cursor:'pointer',
                events: {
                    click: function (data) {
                        var boardName=data.point.name;
                        alert("BoardList:"+boardName);
                        location.href="BoardDetail.html"+"?name="+boardName;

                    }
                },
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },

        series: [{
            data: myData
        }]

    });
}