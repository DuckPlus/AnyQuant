/**
 * Created by duanzhengmou on 6/8/16.
 */

$(document).ready(function () {
    // alert("ready");
    var data="{}";

    init_factor_table(data);   
});

function init_factor_table(allStock) {
    alert(allStock);
    var table = $('#factor_weight').DataTable( {

        data:allStock,
        lengthChange:false,
        pageLength:10,
        dom: 'lrtip',
        "order":[[1,"asc"]],
        columns:[
            {data:'factor'},
            {data:'weight'}
        ],
        /*
         *自适应宽度 默认true 关闭可提升性能,同时方便管理布局
         */
        "autoWidth":false,
        //国际化
        "oLanguage": {
            "sProcessing": "疯狂加载数据中.",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "抱歉， 没有找到",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoEmpty": "没有数据",
            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
            "sZeroRecords": "没有检索到数据",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        }
    } );

}