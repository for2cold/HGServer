var handleDataTableDefault = function() {
    "use strict";
 //   $('#data-loader').removeClass('hide');

    // 获取数据
    $.get();
    0 !== $("#data-table").length && $("#data-table").DataTable({
        responsive: !0
    })
},
TableManageDefault = function() {
    "use strict";
    return {
        init: function() {
            handleDataTableDefault()
        }
    }
}();