$(function () {
    //计算总价
    var array = $(".qprice");
    var totalCost = 0;
    for (var i = 0; i < array.length; i++) {
        var val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥" + totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
});

//商品数量++
function addQuantity(obj) {
    var index = $(".car_btn_2").index(obj);
    var stock = parseInt($(".productStock").eq(index).val());
    var price = parseFloat($(".productPrice").eq(index).val());
    var inputObj = $(".car_ipt").eq(index);
    var quantity = inputObj.val();
    var id = $(".id").eq(index).val();
    ++quantity;
    if (quantity > stock) {
        quantity = stock;
        alert("库存不足！");
    }

    var cost = quantity * price;

    //将最新的quantity和cost更新到数据库
    //RestFul格式，直接将数据添加到url后面即可
    $.ajax({
        url: "/cart/updateCart/" + id + "/" + quantity + "/" + cost,
        type: "POST",
        dataType: "text",
        success: function (data) {
            if (data == "success") {
                //只有数据库修改成功时，才会刷新页面中的内容
                $(".qprice").eq(index).html("￥" + cost.toFixed(2));
                inputObj.val(quantity);
                var array = $(".qprice");
                var totalCost = 0;
                for (var i = 0; i < array.length; i++) {
                    var val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥" + totalCost);
            }
        }

    });

    // $.ajax({
    //     url:"/product/updateCart/"+id+"/"+quantity+"/"+cost,
    //     type:"POST",
    //     dataType:"text",
    //     success:function (data) {
    //         if(data == "success"){
    //             //更新toSettlement的数据
    //             $(".qprice").eq(index).html("￥"+cost);
    //             inputObj.val(quantity);
    //             if(quantity < stock){
    //                 var totalCost = parseInt($("#totalprice").html().substring(1));
    //                 totalCost += price;
    //                 $("#totalprice").html("￥"+totalCost);
    //             }
    //             //更新searchBar的数据
    //             $(".quantity").eq(index).text(quantity);
    //             $(".cost").eq(index).text(cost);
    //
    //             var array = $(".cost");
    //             var totalCost = 0;
    //             for(var i = 0;i < array.length;i++){
    //                 var val = parseInt($(".cost").eq(i).html());
    //                 totalCost += val;
    //             }
    //             $("#totalCost").html("￥"+totalCost);
    //         }
    //     }
    // });
}

//商品数量--
function subQuantity(obj) {
    var index = $(".car_btn_1").index(obj);
    var price = parseFloat($(".productPrice").eq(index).val());
    var inputObj = $(".car_ipt").eq(index);
    var quantity = inputObj.val();
    var id = $(".id").eq(index).val();
    --quantity;
    if (quantity < 1) {
        quantity = 1;
        alert("非法操作！");
    }
    var cost = quantity * price;
    $.ajax({
        url: "/cart/updateCart/" + id + "/" + quantity + "/" + cost,
        type: "POST",
        dataType: "text",
        success: function (data) {
            if (data == "success") {
                //只有数据库修改成功时，才会刷新页面中的内容
                inputObj.val(quantity);
                $(".qprice").eq(index).html("￥" + cost.toFixed(2));
                var array = $(".qprice");
                var totalCost = 0;
                for (var i = 0; i < array.length; i++) {
                    var val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥" + totalCost);
            }
        }
    });


    // $.ajax({
    //     url:"/product/updateCart/"+id+"/"+quantity+"/"+cost,
    //     type:"POST",
    //     dataType:"text",
    //     success:function(data){
    //         if(data == "success"){
    //             $(".qprice").eq(index).html("￥"+cost);
    //             inputObj.val(quantity);
    //             if(quantity!=1){
    //                 var totalCost = parseInt($("#totalprice").html().substring(1));
    //                 totalCost -= price;
    //                 $("#totalprice").html("￥"+totalCost);
    //             }
    //             $(".quantity").eq(index).text(quantity);
    //             $(".cost").eq(index).text(cost);
    //
    //             var array = $(".cost");
    //             var totalCost = 0;
    //             for(var i = 0;i < array.length;i++){
    //                 var val = parseInt($(".cost").eq(i).html());
    //                 totalCost += val;
    //             }
    //             $("#totalCost").html("￥"+totalCost);
    //         }
    //     }
    // });
}

//移出购物车
function removeCart(obj) {
    var index = $(".delete").index(obj);
    var id = parseInt($(".id").eq(index).val());
    if (confirm("是否确定要删除？")) {
        window.location.href = "/cart/delete/" + id;
    }
}

//判断当前购物车是否为空， 非空才能跳转
function settlement2(){
    var totalcost = parseInt($("#totalprice").text().substring(1));
    if(totalcost==0){
        alert("当前购物车为空！")
        return false;
    }
    window.location.href="/order/computeOrder";
}
