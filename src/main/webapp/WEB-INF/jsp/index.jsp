<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search image differences</title>
</head>
<body>
<form action="getDiff" method="post" enctype="multipart/form-data">
    <input name="firstImg" type="file" id="firstFile" accept="image/jpeg,image/png,image/gif">
    <input name="secondImg" type="file" id="secondFile" accept="image/jpeg,image/png,image/gif">
    <input type="submit" value="Search">
</form>
</body>
</html>