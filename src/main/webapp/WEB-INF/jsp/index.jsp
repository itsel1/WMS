<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="col-md-12">
    <div class="col-md-2">
        <form>
            <div class="form-group">
                <label for="img">파일 업로드</label>
                <input type="file" id="img">
            </div>
            <button type="button" class="btn btn-primary" id="btn-save">저장</button>
        </form>
    </div>
    <div class="col-md-10">
        <p><strong>결과 이미지입니다.</strong></p>
        <img src="" id="result-image">
    </div>
</div>


<script>
    $('#btn-save').on('click', uploadImage);

    function uploadImage() {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);

        $.ajax({
            type: 'POST',
            url: '/upload',
            data: formData,
            processData: false,
            contentType: false
        }).done(function (data) {
            $('#result-image').attr("src", data);
        }).fail(function (error) {
            alert(error);
        })
    }
</script>
	
</body>
</html>