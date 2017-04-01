var zTree = function () {

    var zTreeOnClick = function (event, treeId, treeNode) {
       //alert(treeNode.tId + ", " + treeNode.name);
       // // alert($('#'+treeNode.tId+"_a").attr("title"));
       //  console.log($('#'+treeNode.tId+"_a"))
        $('#'+treeNode.tId+"_a").contextmenu({
            target: '#context-menu',
            onItem: function (context, e) {
              alert($(e.target).text() + treeNode.name);
            },
            isDisabled:false
        });
        $('#'+treeNode.tId+"_a").trigger(event);
       //  
       //  
       //  
    };

    var beforeDrag = function (treeId, treeNodes) {
        return false;
    }

    var setting = {
        edit: {
            enable: true
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onRightClick: zTreeOnClick,
            beforeDrag: beforeDrag
        }
    };

    var zNodes;
    
    var setZtree=function(e) {
    	zNodes=e;
    }

    var handleSample = function () {
        $.fn.zTree.init($("#menuTree"), setting, zNodes);
        var zTree = $.fn.zTree.getZTreeObj("menuTree"),
        remove = false,
        rename = false,
        removeTitle = "删除",
        renameTitle = "修改";
        zTree.setting.edit.showRemoveBtn = remove;
        zTree.setting.edit.showRenameBtn = rename;
        zTree.setting.edit.removeTitle = removeTitle;
        zTree.setting.edit.renameTitle = renameTitle;
    }

   
    return {
        //main function to initiate the module
        init: function () {
            handleSample();
            
        },
        setZtreeNode:function (e){
        	setZtree(e);
        }

    };

}();