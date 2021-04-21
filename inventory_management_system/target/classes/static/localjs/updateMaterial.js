$(function () {
    var id = $("#supplier_id").attr("supplierId");
    if (id != "")
        $("#supplier_id").val(id);
});

function back() {
    history.go(-1);
}