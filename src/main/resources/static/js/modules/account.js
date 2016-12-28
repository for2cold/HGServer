if (!data || data.length == 0) {
    data = [["", "", 0.00, 0.00, 0.00, "K1", "", "IDLE", "", "", ""]];
}
var dateOption = {
    format: 'yyyy-mm-dd',
    minView: '2',
    startView: '2',
    autoclose: true,
    startDate: new Date()
};
var $table = $('#accountTable').editTable({
    field_templates: {
    	'index': {
    		html: '<div><span class="index"></span><input type="hidden" name="id"></div>',
    		getValue: function(input) {
    			return $(input).find('input').val();
    		},
    		setValue: function(input, value, index) {
    			var $input = $(input);
    			$input.find('span.index').text(++index);
    			$input.find('input').val(value);
    			return $input;
    		}
    	},
    	'text': {
    	    html: '<div><span class="text amount"></span></div>',
    	    getValue: function(input) {
                return $(input).find('.text').text();
            },
            setValue: function(input, value) {
                var $input = $(input);
                $input.text(value);
                return $input;
            }
    	},
        'textarea' : {
            html: '<div><textarea class="remark"/></div>',
            getValue: function (input) {
                return $(input).val();
            },
            setValue: function (input, value) {
                return $(input).text(value);
            }
        },
        'phone_select': {
        	html: $('#phoneTpl').html(),
            getValue: function (input) {
                return $(input).val();
            },
            setValue: function (input, value) {
                var select = $(input);
                select.find('option').filter(function() {
                    return $(this).val() == value;
                }).attr('selected', true);
                return select;
            }
        },
        'task_select': {
            html: $('#taskTpl').html(),
            getValue: function (input) {
                return $(input).val();
            },
            setValue: function (input, value) {
                var select = $(input);
                select.find('option').filter(function() {
                    return $(this).val() == value;
                }).attr('selected', true);
                return select;
            }
        },
        'alipay_select': {
        	html: $('#alipayTpl').html(),
            getValue: function (input) {
                return $(input).val();
            },
            setValue: function (input, value) {
                var select = $(input);
                select.find('option').filter(function() {
                    return $(this).val() == value;
                }).attr('selected', true);
                return select;
            }
        },
        'status_select' : {
            html: $('#statusTpl').html(),
            getValue: function (input) {
                return $(input).val();
            },
            setValue: function (input, value) {
                var select = $(input);
                select.find('option').filter(function() {
                    return $(this).val() == value;
                }).attr('selected', true);
                return select;
            }
        }
    },
    data_types: ['', '', 'number', 'number', 'number', 'string', 'string', 'number', 'string', 'string', ''],
    field_names: ['id', 'mobile', 'amount', 'balance', 'withdraw_balance', 'phone', 'alipay', 'status', 'deadline', 'withdraw_date', 'remark'],
    field_valids: ['', '请填写账号', '', '', '', '', '', '请选择状态', '', '', ''],
    row_template: ['index', 'input', 'text', 'input', 'input', 'phone_select', 'alipay_select', 'status_select', 'input', 'input', 'textarea'],
    headerCols: ['序号','账号', '已赚金额', '余额', '已提金额', '机器', '支付宝', '状态', '登记日期', '目标日期', '备注'],
    first_row: false,
    hasIndex: true,
    tableClass: 'inputtable',
    add_callback: function($row) {
        $row.find('.selectpicker').css('display', 'block!important').show().selectpicker();
        $row.find('.deadline').datetimepicker(dateOption).on('changeDate', function(ev) {
            var startDate = $(this).val();
            $row.find('.withdraw_date').datetimepicker('setStartDate', startDate);
            $row.find('.withdraw_date').val('');
        });
        $row.find('.withdraw_date').datetimepicker(dateOption);
        $row.find('.amount').text(0);
        $row.find('.balance,.withdraw_balance').val(0);
    },
    update_callback: function($row, data) {
    	var isValid = true;
    	var $validItems = $row.find('[data-valid]');
		var msg = '';
		var $target = null;
		for (var j = 0; j < $validItems.length; j++) {
			var $item = $($validItems[j]);
			if (!$item.val()) {
				$target = $item;
				isValid = false;
				msg = $item.attr('data-valid');
				break;
			}
		}
		if (!isValid) {
			$.gritter.add({
                title: '失败提示~',
                text: msg,
                time: 2000
            });
			$target && $target.focus();
			return false;
		} else {
			$target = null;
		}
        var id = $row.find('[name="id"]').val() || '';
        var mobile = $row.find('.mobile').val() || '';
        var balance = $row.find('.balance').val() || '';
        var withdraw_balance = $row.find('.withdraw_balance').val() || '';
        var phone = $row.find('select.phoneVal').val() || '';
        var alipay = $row.find('select.alipayVal').val() || '';
        var task_type = $row.find('.task_type').val() || '';
        var status = $row.find('select.statusVal').val() || '';
        var deadline = $row.find('.deadline').val() || '';
        var withdraw_date = $row.find('.withdraw_date').val() || '';
        var remark = $row.find('.remark').val() || '';
        var postData = {
            "id": id,
            "mobile": mobile,
            "balance": balance,
            "withdrawBalance": withdraw_balance,
            "phone": phone,
            "alipay": alipay,
            "taskType": task_type,
            "status": status,
            "deadline": deadline,
            "withdrawDate": withdraw_date,
            "remark": remark
        };
        $.post(ctx + '/front/account/update', postData).done(function(resp) {
            if (resp.code == 1000) {
                $.gritter.add({
                    title: '成功提示~',
                    text: resp.msg,
                    class_name: 'gritter-light',
                    time: 2000
                });
            } else {
                $.gritter.add({
                    title: '失败提示~',
                    text: resp.msg,
                    time: 2000
                });
            }
            $row.find('[name="id"]').val(resp.data);
            $row.find('.updaterow').addClass('disabled');
        });
    },
    del_callback: function($row, id, callback) {
        $('#remove-modal').modal();
        $('#remove-id').val(id);
        $('#btn-remove').click(function() {
            var id = $('#remove-id').val();
            if (!id || id == 'null') {
                $('#remove-modal').modal('hide');
                $row.remove();
                callback && callback();
                return false;
            }
            var url = ctx + '/front/account/' + id + '/remove';
            $.post(url).done(function(resp) {
                $('#remove-modal').modal('hide');
                if (resp.code == 1000) {
                    $.gritter.add({
                        title: '成功提示~',
                        text: resp.msg,
                        class_name: 'gritter-light',
                        time: 2000,
                        after_close: function() {
                            $row.remove();
                            callback && callback();
                        }
                    });
                } else {
                    $.gritter.add({
                        title: '失败提示~',
                        text: resp.msg,
                        time: 2000
                    });
                }
            });
        });
    }
});
$(function() {
    // 查询条件
    $('#period').datetimepicker({
        format: 'yyyy-mm',
        minView: 3,
        startView: 3,
        autoclose: true,
        startDate: new Date()
    }).on('changeDate', function() {
        searchForm();
    }).on('change', function() {
        searchForm();
    });
    $('#statusVal,#phoneVal,#alipayVal').change(function() {
        searchForm();
    });
    $table.loadData(data, function() {
        // 下拉框控件
        $('.selectpicker').selectpicker();
        // 日期控件
        $('#accountTable tbody tr').find('.deadline').datetimepicker(dateOption).on('changeDate', function(ev) {
            var $row = $(this).closest('tr');
            var startDate = $(this).val();
            $row.find('.withdraw_date').datetimepicker('setStartDate', startDate);
            $row.find('.withdraw_date').val('');
        });
        $('#accountTable tbody tr').find('.withdraw_date').datetimepicker(dateOption);
        $('#accountTable tbody tr').find('select.statusVal').each(function() {
            var style = $(this).find('option:selected').attr('data-class');
            $(this).closest('.bootstrap-select').find('.dropdown-toggle').attr('class', 'btn dropdown-toggle ' + style);
        });

    });
    $('body').on('changed.bs.select', 'select.statusVal', function() {
        var style = $(this).find('option:selected').attr('data-class');
        $(this).closest('.bootstrap-select').find('.dropdown-toggle').attr('class', 'btn dropdown-toggle ' + style);
    });
    // 收入报表
    $('#reportBtn').click(function() {
        // loading
        var period = $('#period').val();
        var url = ctx + '/front/account/reports?period=' + period;
        $.get(url).done(function(resp) {
            if (resp.code == 1000) {
                $('#report-modal').modal();
                var datas = resp.data;
                $('#report-modal table tbody').html('');
                if (datas && datas.length > 0) {
                    for (var i = 0; i < datas.length; ++i) {
                        var report = datas[i];
                        var $tr = $('<tr><td class="index"></td><td class="date"></td><td class="amount"></td></tr>');
                        $tr.find('.index').text((i + 1));
                        $tr.find('.date').text(report.date);
                        $tr.find('.amount').text(report.amount);
                        if (report.amount >= 300) {
                            $tr.find('.amount').addClass('text-primary')
                        }
                        $('#report-modal table tbody').append($tr);
                    }
                } else {
                    $('#report-modal table tbody').html('<tr><td class="text-center" colspan="3">暂无收入</td></tr>');
                }
            }
        });
    });
});
function searchForm() {
    $('#searchForm').submit();
}