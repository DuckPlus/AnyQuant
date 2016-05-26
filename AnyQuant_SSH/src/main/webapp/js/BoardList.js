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
    while(i<9){
        var newNum=Math.floor(Math.random()*(length+1));
        if(ifExist(index,newNum)==0){
            index[i]=newNum;
            i++;
        }
    }
    for(var i=0;i<index.length;i++){
        if(i<index.length/3){
            myData[i]=({
                x: 5*i,
                y: 50+Math.random()*10,
                z: 50,//大小
                name: globalData[index[i]]
            });
        }else if(i<2*index.length/3){
                myData[i]=({
                    x: (i-index.length/3),
                    y: 100+Math.random()*10,
                    z: 50,//大小
                    name: globalData[index[i]]
                });
            
        }else{
                myData[i]=({
                    x: (i-2*index.length/3),
                    y: 150+Math.random()*10,
                    z: 50,//大小
                    name: globalData[index[i]]
                });
        }
        
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
                //color:"#FFFFFF",
                negativeColor:"",
                cursor:'pointer',
                events: {
                    click: function (data) {
                        var boardName=data.point.name;
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