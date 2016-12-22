/*! editTable v0.2.0 by Alessandro Benoit */
(function ($, window, i) {

    'use strict';

    $.fn.editTable = function (options) {

        // Settings
        var s = $.extend({
                data: [['']],
                tableClass: 'inputtable',
                jsonData: false,
                headerCols: false,
                maxRows: 999,
                first_row: true,
                row_template: false,
                field_templates: false,
                field_names: false,
                sort_fields: false,
                sort_form_id: false,
                field_valids: false,
                hasIndex: false,
                validate_field: function (col_id, value, col_type, $element) {
                    return true;
                },
                add_callback: function($row) {

                },
                del_callback: function($row, id, callback) {

                },
                update_callback: function($row, data) {

                }
            }, options),
            $el = $(this),
            defaultTableContent = '<thead><tr></tr></thead><tbody></tbody>',
            $table = $('<table/>', {
                class: s.tableClass + ((s.first_row) ? ' wh' : ''),
                html: defaultTableContent
            }),
            defaultth = '<th><a class="addcol icon-button" href="#">√</a> <a class="addcol icon-button" href="#">+</a> <a class="delcol icon-button" href="#">-</a></th>',
            colnumber,
            rownumber,
            reset,
            is_validated = true;

        // Increment for IDs
        i = i + 1;

        // Build cell
        function buildCell(content, type, colIndex, rowIndex) {
            content = (content === 0) ? "0" : (content || '');
            var field_name = s.field_names[colIndex];
            var field_valid = s.field_valids[colIndex];
            var data_valid = 'data-valid="' + field_valid + '"';
            if (!field_valid) {
                data_valid = '';
            }
            // Custom type
            if (type && 'input' !== type){
                var field = s.field_templates[type];
                var html = field.html;
                if (type !== 'index') {                    
                    var $html = $(field.html);
                    if (!$html.find('select,input').attr('name')) {
                        $html.find('select,input').attr('name', field_name);
                    }
                    if (!$html.find('select.input').attr('data-valid') && data_valid) {
                        $html.find('select,input').attr('data-valid', field_valid);
                    }
                    html = $html.html();
                }
                return '<td>' + field.setValue(html, content, rowIndex)[0].outerHTML + '</td>';
            }
            // Default
            return '<td><input type="text" ' + data_valid + ' class="' + field_name + '" name="' + field_name + '" value="' + content.toString().replace(/"/g, "&quot;") + '" /></td>';
        }

        // Build row
        function buildRow(data, len, index) {

            var rowcontent = '', b;

            data = data || '';

            if (!s.row_template) {
                // Without row template
                for (b = 0; b < (len || data.length); b += 1) {
                    rowcontent += buildCell(data[b], null, b, index);
                }
            } else {
                // With row template
                for (b = 0; b < s.row_template.length; b += 1) {
                    // For each field in the row
                    rowcontent += buildCell(data[b], s.row_template[b], b, index);
                }
            }

            return $('<tr/>', {
                html: rowcontent + '<td><a class="updaterow disabled icon-button" href="#">√</a> <a class="addrow icon-button" href="#">+</a> <a class="delrow icon-button" href="#">-</a></td>'
            });

        }


        // Update Index
        function updateIndex(className) {
            className = className || 'index';
            $table.find('tbody tr').each(function(index) {
                $(this).find('.' + className).text((index + 1));
            });
        }

        // Check button status (enable/disabled)
        function checkButtons() {
            if (colnumber < 2) {
                $table.find('.delcol').addClass('disabled');
            }
            if (rownumber < 2) {
                $table.find('.delrow').addClass('disabled');
            }
            if (s.maxRows && rownumber === s.maxRows) {
                $table.find('.addrow').addClass('disabled');
            }
        }

        // Fill table with data
        function fillTableData(data, callback) {

            var a, crow = Math.min(s.maxRows, data.length);

            // Clear table
            $table.html(defaultTableContent);

            // If headers or row_template are set
            if (s.headerCols || s.row_template) {

                // Fixed columns
                var col = s.headerCols || s.row_template;

                // Table headers
                for (a = 0; a < col.length; a += 1) {
                    var col_title = s.headerCols[a] || '';
                    var sort_field = s.sort_fields[a] || '';
                    if (sort_field) {
                        $table.find('thead tr').append('<th>' + col_title + ' <span class="fa fa-angle-down pointer" data-sort-field="' + sort_field + '"></span></th>');
                    } else {
                        $table.find('thead tr').append('<th>' + col_title + '</th>');
                    }
                }

                // Table content
                for (a = 0; a < crow; a += 1) {
                    // For each row in data
                    buildRow(data[a], col.length, a).appendTo($table.find('tbody'));
                }

            } else if ( data[0] ) {

                // Variable columns
                for (a = 0; a < data[0].length; a += 1) {
                    $table.find('thead tr').append(defaultth);
                }

                for (a = 0; a < crow; a += 1) {
                    buildRow(data[a]).appendTo($table.find('tbody'));
                }

            }

            // Append missing th
            $table.find('thead tr').append('<th></th>');

            // Count rows and columns
            colnumber = $table.find('thead th').length - 1;
            rownumber = $table.find('tbody tr').length;

            checkButtons();

            callback && callback();
            if (s.sort_form_id) {
                sortEvent(s.sort_form_id);
            }
        }

        // Listener Event
        function changeListener($row) {

            if ($row) {
                $row.find('input,select,textarea').change(function() {
                    $row.find('.updaterow').removeClass('disabled');
                });
            } else {
                $table.find('input,select,textarea').change(function() {
                    var $row = $(this).closest('tr');
                    $row.find('.updaterow').removeClass('disabled');
                });
            }
        }

        // 排序
        function sortEvent($formId) {
            $table.on('click', '[data-sort-field]', function() {
                console.debug('type-->' + $(this).attr('data-sort-field'));
            });
        }

        // Export data
        function exportData() {
            var row = 0, data = [], value;

            is_validated = true;

            $table.find('tbody tr').each(function () {

                row += 1;
                data[row] = [];

                $(this).find('td:not(:last-child)').each(function (i, v) {
                    if ( s.row_template && 'input' !== s.row_template[i] ){
                        var field = s.field_templates[s.row_template[i]],
                            el = $(this).find($(field.html).prop('tagName'));
                        
                        value = field.getValue(el);
                        if ( !s.validate_field(i, value, s.row_template[i], el) ){
                            is_validated = false;
                        }
                        data[row].push(value);
                    } else {
                        value = $(this).find('input[type="text"]').val();
                        if ( !s.validate_field(i, value, 'input', v) ){
                            is_validated = false;
                        }
                        data[row].push(value);
                    }
                });
                
            });

            // Remove undefined
            data.splice(0, 1);

            return data;
        }

        // Fill the table with data from textarea or given properties
        if ($el.is('textarea')) {

            try {
                reset = JSON.parse($el.val());
            } catch (e) {
                reset = s.data;
            }

            $el.after($table);

            // If inside a form set the textarea content on submit
            if ($table.parents('form').length > 0) {
                $table.parents('form').submit(function () {
                    $el.val(JSON.stringify(exportData()));
                });
            }

        } else {
            reset = (JSON.parse(s.jsonData) || s.data);
            $el.append($table);
        }

        fillTableData(reset);

        // Add column
        $table.on('click', '.addcol', function () {

            var colid = parseInt($(this).closest('tr').children().index($(this).parent('th')), 10);

            colnumber += 1;

            $table.find('thead tr').find('th:eq(' + colid + ')').after(defaultth);

            $table.find('tbody tr').each(function () {
                $(this).find('td:eq(' + colid + ')').after(buildCell());
            });

            $table.find('.delcol').removeClass('disabled');

            return false;
        });

        // Remove column
        $table.on('click', '.delcol', function () {

            if ($(this).hasClass('disabled')) {
                return false;
            }

            var colid = parseInt($(this).closest('tr').children().index($(this).parent('th')), 10);

            colnumber -= 1;

            checkButtons();

            $(this).parent('th').remove();

            $table.find('tbody tr').each(function () {
                $(this).find('td:eq(' + colid + ')').remove();
            });

            return false;
        });

        // Add row
        $table.on('click', '.addrow', function () {

            if ($(this).hasClass('disabled')) {
                return false;
            }

            rownumber += 1;

            $(this).closest('tr').after(buildRow(0, colnumber));

            $table.find('.delrow').removeClass('disabled');
            var $target = $(this).closest('tr').next();
            $target.find('.updaterow').addClass('disabled');
            changeListener($target);
            checkButtons();

            if (s.hasIndex) {
                updateIndex();
            }
            s.add_callback($target);

            return false;
        });

        // Update row
        $table.on('click', '.updaterow', function () {

            if ($(this).hasClass('disabled')) {
                return false;
            }
            var $row = $(this).closest('tr');
            var rowdata = {};
            $row.find('[name]').each(function() {
                var name = $(this).attr('name');
                if (name) {
                    rowdata[name] = $(this).val();
                }
            });

            s.update_callback($row, rowdata);

            return false;
        });

        // Delete row
        $table.on('click', '.delrow', function () {

            if ($(this).hasClass('disabled')) {
                return false;
            }

            var id = $(this).closest('tr').find('[name="id"]').val();

            s.del_callback($(this).closest('tr'), id, function() {

                rownumber -= 1;
                checkButtons();
                $table.find('.addrow').removeClass('disabled');
                if (s.hasIndex) {
                    updateIndex();
                }
            });
            return false;
        });

        // Select all content on click
        $table.on('click', 'input,textarea', function () {
            $(this).select();
        });

        // Return functions
        return {
            // Get an array of data
            getData: function () {
                return exportData();
            },
            // Get the JSON rappresentation of data
            getJsonData: function () {
                return JSON.stringify(exportData());
            },
            // Load an array of data
            loadData: function (data, callback) {
                fillTableData(data, callback);
                changeListener();
            },
            // Load a JSON rappresentation of data
            loadJsonData: function (data) {
                fillTableData(JSON.parse(data));
            },
            // Reset data to the first instance
            reset: function () {
                fillTableData(reset);
            },
            isValidated: function () {
                return is_validated;
            }
        };
    };

})(jQuery, this, 0);