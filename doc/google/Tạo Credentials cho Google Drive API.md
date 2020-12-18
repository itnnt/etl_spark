### 1- Tạo Credentials
Giả sử bạn có một tài khoản Gmail là abc@gmail.com, khi đó Google sẽ cung cấp cho bạn 15GB dung lượng ổ cứng miễn phí trên Google Drive. Bạn có thể lưu trữ các tập tin của bạn trên đó. Để ứng dụng của bạn có thể thao tác với các tập tin trên Google Drive, nó cần có một Credentials (giấy ủy nhiệm). Credentials đơn giản là một file, nó sẽ được đặt trên máy tính mà ứng dụng của bạn đang được triển khai. Giống như hình minh họa dưới đây:
![](https://s1.o7planning.com/vi/11917/images/20586219.png)

Bạn cần tạo một project trên Google API Console, tạo một Credential (Giấy ủy nhiệm), và download tập tin Credential này về máy tính của bạn.
Trước hết hãy đăng nhập vào tài khoản Gmail của bạn, sau đó truy cập vào đường link dưới đây:

https://console.developers.google.com/start/api?id=drive

Nhấn vào "Continue" để bắt đầu tạo một project.
![](https://s1.o7planning.com/vi/11917/images/20586663.png)
![](https://s1.o7planning.com/vi/11917/images/20586731.png)

Trên trang "Create Credential" nhấn vào nút "Cancel" để bỏ qua.

![](https://s1.o7planning.com/vi/11917/images/20586864.png)

Trên tab "OAuth consent screen", nhập vào email của bạn và Product name (Tên sản phẩm, ở đây bạn nên nhập vào tên công ty của bạn)

![](https://s1.o7planning.com/vi/11917/images/20587272.png)

![](https://s1.o7planning.com/vi/11917/images/20587362.png)

![](https://s1.o7planning.com/vi/11917/images/20587436.png)

Chọn kiểu ứng dụng là Other, và nhấn nút Create.

![](https://s1.o7planning.com/vi/11917/images/20587502.png)

![](https://s1.o7planning.com/vi/11917/images/20587895.png)

Nhấn vào biểu tượng Download để download tập tin Credentials, tập tin này có định dạng JSON.

![](https://s1.o7planning.com/vi/11917/images/20588012.png)

Sau khi download bạn có được một file với tên rất dài, hãy đổi tên của nó thành client_secret.json

![](https://s1.o7planning.com/vi/11917/images/20588796.png)

###2- Enable Google Drive API

Tiếp theo bạn cần kích hoạt Google Drive API.

https://console.cloud.google.com/apis/library

![](https://s1.o7planning.com/vi/11917/images/64412015.png)

![](https://s1.o7planning.com/vi/11917/images/64412019.png)



