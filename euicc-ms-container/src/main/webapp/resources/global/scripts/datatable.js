/***
Wrapper/Helper Class for datagrid based on jQuery Datatable Plugin
***/
var Datatable = function() {

    var tableOptions; // main options
    var dataTable; // datatable object
    var table; // actual table jquery object
    var tableContainer; // actual table container object
    var tableWrapper; // actual table wrapper jquery object
    var tableInitialized = false;
    var ajaxParams = {}; // set filter mode
    var the;

    var countSelectedRecords = function() {
        var selected = $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        var text = tableOptions.dataTable.language.metronicGroupActions;
        if (selected > 0) {
            $('.table-group-actions > span', tableWrapper).text(text.replace("_TOTAL_", selected));
        } else {
            $('.table-group-actions > span', tableWrapper).text("");
        }
    };

    //build ArrayData with fields
    var buildData = function (data , fields) {
    	var rtnData = new Array();
    	 $.each(data, function(key, obj) {
    		 var array = new Array();
    		 $.each(fields, function(k, v) {
    			 if(v.wrapper){
    				 var offset = 0;
    				 var html = v.wrapper;
    				 do
				        {
				            offset = html.indexOf('$field$', offset);
				            if(offset != -1)
				            {	
				            	var va = obj[v.field];
				            	if(!va) va='';
				            	html = html.replace('$field$',va);
				                offset += va.length;
				            }
				        }while(offset != -1)
				     if(v.fieldRender){
				    	 html = eval(v.fieldRender+"('"+htm+"')");
				     }
    				 array.push(html);
    			 }else{
    				 var htm = obj[v.field];
    				 var fldType = (typeof htm);
    				 if(fldType!='string' && htm == 0){
    					 htm = "0";
    				 }
    				 if(!htm) htm='';
    				 if(v.fieldRender){
				    	 htm = eval(v.fieldRender+"('"+htm+"')");
				     }
    				 array.push(htm);
    			 }
    		 });
    		 rtnData.push(array);
         });
    	 return rtnData;
    };

    return {

        //main function to initiate the module
        init: function(options) {

            if (!$().dataTable) {
                return;
            }

            the = this;

            // default settings
            options = $.extend(true, {
                src: "", // actual table
                filterApplyAction: "filter",
                filterCancelAction: "filter_cancel",
                resetGroupActionInputOnSuccess: true,
                loadingMessage: '加载中...',
                dataTable: {
                    "dom": "<'row'<'col-md-8 col-sm-12' l><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r><'table-scrollable't><'row'<'col-md-4 col-sm-12' i><'col-md-8 col-sm-12'p>>", // datatable layout
                    "pageLength": 10, // default records per page
                    "language": { // language settings
                        // metronic spesific
                        "metronicGroupActions": "_TOTAL_ 条记录已选择:  ",
                        "metronicAjaxRequestGeneralError": "请求失败. 请检查你的网络",

                        // data tables spesific
                        "lengthMenu": "分页大小 _MENU_ ",
                        "info": "总记录条数 _TOTAL_",
                        "infoEmpty": "查询无记录",
                        "emptyTable": "查询无记录",
                        "zeroRecords": "没要找到符合的数据...",
                        "paginate": {
                            "previous": "上一页",
                            "next": "下一页",
                            "last": "最后一页",
                            "first": "第一页",
                            "page": "当前页",
                            "pageOf": "总页数"
                        }
                    },

                    "orderCellsTop": true,
                    "columnDefs": [{ // define columns sorting options(by default all columns are sortable extept the first checkbox column)
                        'orderable': false,
                        'targets': [0]
                    }],
                    "columns": [0],
                    "formSearch" : "",
//                    "pagingType": "bootstrap_extended", // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
                    "autoWidth": false, // disable fixed width and enable fluid table
                    "processing": false, // enable/disable display message box on record load
                    "serverSide": true, // enable/disable server side ajax loading

                    "ajax": { // define ajax settings
                        "url": "", // ajax URL
                        "type": "POST", // request type
                        "timeout": 20000,
                        "data": function(data) { // add request parameters before submit
                        	var $searchFrom = $('#'+options.dataTable.formSearch);
                        	//加载searchForm的查询项
                        	$searchFrom.find("input,select").each(function() {
                                //alert($(this).attr("name")+ " : " + $(this).val());
                        		the.setAjaxParam($(this).attr("name"), $(this).val());
                            });
                        	//加载自定义的查询项(json字符串)
                        	if(options.dataTable.queryParams){
                        		//alert(options.dataTable.queryParams);
	                        	var jsonText=JSON.stringify(options.dataTable.queryParams);
	                        	JSON.parse(jsonText,function(key,value){
	                        		//alert("key : "+key+ " |  value:  "+value);
	                        		if(key){
	                        			if(value === '$hidden'){
	                        				the.setAjaxParam(key, $("#"+key).val());
	                        			}else{
	                        				the.setAjaxParam(key, value);
	                        			}
	                        		}
	                        	});
                        	}
                            $.each(ajaxParams, function(key, value) {
                            	// alert("key : "+key+ " |  value:  "+value);
                            	data[key] = value;
                            });
                            Metronic.blockUI({
                                message: tableOptions.loadingMessage,
                                target: tableContainer,
                                overlayColor: 'none',
                                cenrerY: true,
                                boxed: true
                            });
                        },
                        "dataSrc": function(res) { // Manipulate the data returned from the server
                        	var cols = options.dataTable.columns;
                            if (res.customActionMessage) {
                                Metronic.alert({
                                    type: (res.customActionStatus == 'OK' ? 'success' : 'danger'),
                                    icon: (res.customActionStatus == 'OK' ? 'check' : 'warning'),
                                    message: res.customActionMessage,
                                    container: tableWrapper,
                                    place: 'prepend'
                                });
                            }

                            if (res.customActionStatus) {
                                if (tableOptions.resetGroupActionInputOnSuccess) {
                                    $('.table-group-action-input', tableWrapper).val("");
                                }
                            }

                            if ($('.group-checkable', table).size() === 1) {
                                $('.group-checkable', table).attr("checked", false);
                                $.uniform.update($('.group-checkable', table));
                            }

                            if (tableOptions.onSuccess) {
                                tableOptions.onSuccess.call(undefined, the);
                            }

                            Metronic.unblockUI(tableContainer);
                            return buildData(res.data,cols);
//                            return res.data;
                        },
                        "error": function() { // handle general connection errors
                            if (tableOptions.onError) {
                                tableOptions.onError.call(undefined, the);
                            }

                            Metronic.alert({
                                type: 'danger',
                                icon: 'warning',
                                message: tableOptions.dataTable.language.metronicAjaxRequestGeneralError,
                                container: tableWrapper,
                                place: 'prepend'
                            });

                            Metronic.unblockUI(tableContainer);
                        }
                    },

                    "drawCallback": function(oSettings) { // run some code on table redraw
                        if (tableInitialized === false) { // check if table has been initialized
                            tableInitialized = true; // set table initialized
                            table.show(); // display table
                        }
                        Metronic.initUniform($('input[type="checkbox"]', table)); // reinitialize uniform checkboxes on each table reload
                        countSelectedRecords(); // reset selected records indicator

                        // callback for ajax data load
                        if (tableOptions.onDataLoad) {
                            tableOptions.onDataLoad.call(undefined, the);
                        }
                    }
                }
            }, options);
            tableOptions = options;

            // create table's jquery object
            table = $(options.src);
            tableContainer = table.parents(".table-container");

            // apply the special class that used to restyle the default datatable
            var tmp = $.fn.dataTableExt.oStdClasses;

            $.fn.dataTableExt.oStdClasses.sWrapper = $.fn.dataTableExt.oStdClasses.sWrapper + " dataTables_extended_wrapper";
            $.fn.dataTableExt.oStdClasses.sFilterInput = "form-control input-small input-sm input-inline";
            $.fn.dataTableExt.oStdClasses.sLengthSelect = "form-control input-xsmall input-sm input-inline";

            // initialize a datatable
            dataTable = table.DataTable(options.dataTable);

            // revert back to default
            $.fn.dataTableExt.oStdClasses.sWrapper = tmp.sWrapper;
            $.fn.dataTableExt.oStdClasses.sFilterInput = tmp.sFilterInput;
            $.fn.dataTableExt.oStdClasses.sLengthSelect = tmp.sLengthSelect;

            // get table wrapper
            tableWrapper = table.parents('.dataTables_wrapper');

            // build table group actions panel
            if ($('.table-actions-wrapper', tableContainer).size() === 1) {
                $('.table-group-actions', tableWrapper).html($('.table-actions-wrapper', tableContainer).html()); // place the panel inside the wrapper
                $('.table-actions-wrapper', tableContainer).remove(); // remove the template container
            }
            // handle group checkboxes check/uncheck
            $('.group-checkable', table).change(function() {
                var set = $('tbody > tr > td:nth-child(1) input[type="checkbox"]', table);
                var checked = $(this).is(":checked");
                $(set).each(function() {
                    $(this).attr("checked", checked);
                });
                $.uniform.update(set);
                countSelectedRecords();
            });

            // handle row's checkbox click
            table.on('change', 'tbody > tr > td:nth-child(1) input[type="checkbox"]', function() {
                countSelectedRecords();
            });

            // handle filter submit button click
            table.on('click', '.filter-submit', function(e) {
                e.preventDefault();
                the.submitFilter();
            });

            // handle filter cancel button click
            table.on('click', '.filter-cancel', function(e) {
                e.preventDefault();
                the.resetFilter();
            });
        },

        submitFilter: function() {
            the.setAjaxParam("action", tableOptions.filterApplyAction);

            // get all typeable inputs
            $('textarea.form-filter, select.form-filter, input.form-filter:not([type="radio"],[type="checkbox"])', table).each(function() {
                the.setAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all checkboxes
            $('input.form-filter[type="checkbox"]:checked', table).each(function() {
                the.addAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all radio buttons
            $('input.form-filter[type="radio"]:checked', table).each(function() {
                the.setAjaxParam($(this).attr("name"), $(this).val());
            });

            dataTable.ajax.reload();
        },

        resetFilter: function() {
            $('textarea.form-filter, select.form-filter, input.form-filter', table).each(function() {
                $(this).val("");
            });
            $('input.form-filter[type="checkbox"]', table).each(function() {
                $(this).attr("checked", false);
            });
            the.clearAjaxParams();
            the.addAjaxParam("action", tableOptions.filterCancelAction);
            dataTable.ajax.reload();
        },

        getSelectedRowsCount: function() {
            return $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        },

        getSelectedRows: function() {
            var rows = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function() {
                rows.push($(this).val());
            });

            return rows;
        },

        setAjaxParam: function(name, value) {
            ajaxParams[name] = value;
        },

        addAjaxParam: function(name, value) {
            if (!ajaxParams[name]) {
                ajaxParams[name] = [];
            }

            skip = false;
            for (var i = 0; i < (ajaxParams[name]).length; i++) { // check for duplicates
                if (ajaxParams[name][i] === value) {
                    skip = true;
                }
            }

            if (skip === false) {
                ajaxParams[name].push(value);
            }
        },

        clearAjaxParams: function(name, value) {
            ajaxParams = {};
        },

        getDataTable: function() {
            return dataTable;
        },

        getTableWrapper: function() {
            return tableWrapper;
        },

        gettableContainer: function() {
            return tableContainer;
        },

        getTable: function() {
            return table;
        }

    };

};