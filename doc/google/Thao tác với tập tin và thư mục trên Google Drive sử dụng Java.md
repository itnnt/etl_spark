###1- Mục tiêu của bài học
Đã bao giờ bạn xây dựng một ứng dụng Web mà ứng dụng đó cho phép người dùng upload ảnh hoặc các file đính kèm (attachments)? OK nếu có, trong trường hợp này các tập tin mà người dùng Upload lên sẽ được lưu trữ trên ổ cứng của máy chủ của bạn. Và một khi dữ liệu đó ngày càng nhiều bạn cần phải tìm đến một dịch vụ cho thuê nơi lưu trữ thay vì mở rộng thêm kích thước ổ cứng của bạn.

Google Drive chính là một dịch vụ như vậy, nó cung cấp cho bạn khả năng lưu trữ và chia sẻ file. Và quan trọng hơn nó cung cấp API cho phép ứng dụng của bạn thao tác (Manipulate) với các tập tin trên Google Drive.

Trong bài học này tôi hướng dẫn bạn thao tác với Google Drive thông qua Google Drive Java API. Các chủ đề sẽ được thảo luận trong bài học này bao gồm:
- Tạo một ứng dụng Java và khai báo các thư viện để sử dụng Google Drive API.
- Tạo chứng chỉ (Credentials) để có thể tương tác với Google Drive.
- Thao tác với các tập tin và thư mục trên Google Drive thông qua Google Drive API.

###2- Tạo Credentials
Giả sử bạn có một tài khoản Gmail là abc@gmail.com, khi đó Google sẽ cung cấp cho bạn 15GB dung lượng ổ cứng miễn phí trên Google Drive. Bạn có thể lưu trữ các tập tin của bạn trên đó. Để ứng dụng của bạn có thể thao tác với các tập tin trên Google Drive, nó cần có một Credentials (giấy ủy nhiệm). Credentials đơn giản là một file, nó sẽ được đặt trên máy tính mà ứng dụng của bạn đang được triển khai. Giống như hình minh họa dưới đây:

![](https://s1.o7planning.com/vi/11889/images/20520501.png)

![](https://s1.o7planning.com/vi/11889/images/64412031.png)

Để có một Credentials (Giấy ủy nhiệm) nói trên, bạn cần tạo một Project trên Google Developer Console, và download về file client_secret.json.

###3- Java Google Drive API
Với các project sử dụng Maven, bạn cần khai báo các phụ thuộc (dependencies) sau:
```xml
<!-- https://mvnrepository.com/artifact/com.google.apis/google-api-services-drive -->
<dependency>
    <groupId>com.google.apis</groupId>
    <artifactId>google-api-services-drive</artifactId>
    <version>v3-rev105-1.23.0</version>
</dependency>
 
 
<!-- https://mvnrepository.com/artifact/com.google.api-client/google-api-client -->
<dependency>
    <groupId>com.google.api-client</groupId>
    <artifactId>google-api-client</artifactId>
    <version>1.23.0</version>
</dependency>
 
<!-- https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client-jetty -->
<dependency>
    <groupId>com.google.oauth-client</groupId>
    <artifactId>google-oauth-client-jetty</artifactId>
    <version>1.23.0</version>
</dependency>
```
###4- Bắt đầu nhanh với một ví dụ
Tạo một dự án Maven, và tạo lớp DriveQuickstart để bắt đầu nhanh với Google Drive API.
![](https://s1.o7planning.com/vi/11889/images/20602641.png)

DriveQuickstart.java
```java
package org.o7planning.googledrive.quickstart;
 
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
 
public class DriveQuickstart {
 
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
 
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
 
    // Directory to store user credentials for this application.
    private static final java.io.File CREDENTIALS_FOLDER //
            = new java.io.File(System.getProperty("user.home"), "credentials");
 
    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
 
    //
    // Global instance of the scopes required by this quickstart. If modifying these
    // scopes, delete your previously saved credentials/ folder.
    //
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
 
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
 
        java.io.File clientSecretFilePath = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
 
        if (!clientSecretFilePath.exists()) {
            throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME //
                    + " to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
        }
 
        // Load client secrets.
        InputStream in = new FileInputStream(clientSecretFilePath);
 
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
 
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
                        .setAccessType("offline").build();
 
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
 
    public static void main(String... args) throws IOException, GeneralSecurityException {
 
        System.out.println("CREDENTIALS_FOLDER: " + CREDENTIALS_FOLDER.getAbsolutePath());
 
        // 1: Create CREDENTIALS_FOLDER
        if (!CREDENTIALS_FOLDER.exists()) {
            CREDENTIALS_FOLDER.mkdirs();
 
            System.out.println("Created Folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
            System.out.println("Copy file " + CLIENT_SECRET_FILE_NAME + " into folder above.. and rerun this class!!");
            return;
        }
 
        // 2: Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
 
        // 3: Read client_secret.json file & create Credential object.
        Credential credential = getCredentials(HTTP_TRANSPORT);
 
        // 5: Create Google Drive Service.
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) //
                .setApplicationName(APPLICATION_NAME).build();
 
        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list().setPageSize(10).setFields("nextPageToken, files(id, name)").execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }
}
```

rong lần đầu tiên chạy lớp DriveQuickstart, một thư mục {user_home}/credentials sẽ được tạo ra nếu nó không tồn tại.

![](https://s1.o7planning.com/vi/11889/images/20602891.png)

Copy tập tin client_secret.json vào thư mục nói trên:

![](https://s1.o7planning.com/vi/11889/images/20602948.png)

Chạy lại lớp DriveQuickstart một lần nữa. Trên cửa sổ Console copy đường Link và truy cập vào nó trên trình duyệt.

![](https://s1.o7planning.com/vi/11889/images/20603021.png)

Khi bạn truy cập vào liên kết (Link) đó trên trình duyệt, nó sẽ yêu cầu bạn đăng nhập với một tài khoản Gmail. Lúc này bạn cần đăng nhập với tài khoản Gmail mà bạn đã tạo ra tập tin client_secret.json.

![](https://s1.o7planning.com/vi/11889/images/20603148.png)

![](https://s1.o7planning.com/vi/11889/images/20603184.png)

![](https://s1.o7planning.com/vi/11889/images/20603232.png)

Lúc này một file có tên StoredCredential sẽ được tạo ra trên thư mục {user_home}/credential.

![](https://s1.o7planning.com/vi/11889/images/20603267.png)

Credential (Giấy ủy quyền) đã được lưu trên ổ cứng máy tính của bạn. Từ bây giờ làm việc với Google Drive bạn không phải trải qua các bước như trên nữa.
Chạy lại lớp DriveQuickstart một lần nữa, và xem kết quả trên cửa sổ Console, chương trình sẽ in ra danh sách các tập tin và thư mục trên Google Drive của bạn.

![](https://s1.o7planning.com/vi/11889/images/20603443.png)

###5- Cấu trúc File và Directory của Google Drive

Khái niệm về thư mục (Directory) và tập tin (File) trong Google Drive hơi khác biệt so với khái niệm Directory & File trên các hệ điều hành. Sau đây là các đặc điểm cơ bản:
- Trong Google Drive một File/Directory có thể có một hoặc nhiều Directory cha.
- Trong cùng một thư mục, các file có thể có tên giống nhau, nhưng ID khác nhau.
- Lớp com.google.api.services.drive.model.File đại diện cho cả hai, File và Directory.
Lớp com.google.api.services.drive.model.File có rất nhiều trường (field) như hình minh họa dưới đây:

![](https://s1.o7planning.com/vi/11889/images/20685730.png)

Có 2 trường (field) quan trọng đó là mineType & parents:
parents
Danh sách ID của các thư mục cha của tập tin (thư mục) hiện tại. Các thư mục gốc (root) hoặc tập tin gốc có thư mục cha với ID = "root".

mineType

| MIME Type                                	| Description          	|
|------------------------------------------	|----------------------	|
| application/vnd.google-apps.audio        	|                      	|
| application/vnd.google-apps.document     	| Google Docs          	|
| application/vnd.google-apps.drawing      	| Google Drawing       	|
| application/vnd.google-apps.file         	| Google Drive file    	|
| application/vnd.google-apps.folder       	| Google Drive folder  	|
| application/vnd.google-apps.form         	| Google Forms         	|
| application/vnd.google-apps.fusiontable  	| Google Fusion Tables 	|
| application/vnd.google-apps.map          	| Google My Maps       	|
| application/vnd.google-apps.photo        	|                      	|
| application/vnd.google-apps.presentation 	| Google Slides        	|
| application/vnd.google-apps.script       	| Google Apps Scripts  	|
| application/vnd.google-apps.site         	| Google Sites         	|
| application/vnd.google-apps.spreadsheet  	| Google Sheets        	|
| application/vnd.google-apps.unknown      	|                      	|
| application/vnd.google-apps.video        	|                      	|
| application/vnd.google-apps.drive-sdk    	| 3rd party shortcut   	|

Các toán tử sử dụng cho các trường (field):

| Field          	| Value Type 	| Operators           	| Description                                                                                                                   	|
|----------------	|------------	|---------------------	|-------------------------------------------------------------------------------------------------------------------------------	|
| name           	| string     	| contains, =, !=     	| Name of the file.                                                                                                             	|
| fullText       	| string     	| contains            	| Full text of the file including name, description, content, and indexable text.                                               	|
| mimeType       	| string     	| contains, =, !=     	| MIME type of the file.                                                                                                        	|
| modifiedTime   	| date       	| <=, <, =, !=, >, >= 	| Date of the last modification of the file.                                                                                    	|
| viewedByMeTime 	| date       	| <=, <, =, !=, >, >= 	| Date that the user last viewed a file.                                                                                        	|
| trashed        	| boolean    	| =, !=               	| Whether the file is in the trash or not.                                                                                      	|
| starred        	| boolean    	| =, !=               	| Whether the file is starred or not.                                                                                           	|
| parents        	| collection 	| in                  	| Whether the parents collection contains the specified ID.                                                                     	|
| owners         	| collection 	| in                  	| Users who own the file.                                                                                                       	|
| writers        	| collection 	| in                  	| Users or groups who have permission to modify the file.                                                                       	|
| readers        	| collection 	| in                  	| Users or groups who have permission to read the file.                                                                         	|
| sharedWithMe   	| boolean    	| =, !=               	| Files that are in the user's "Shared with me" collection.                                                                     	|
| properties     	| collection 	| has                 	| Public custom file properties.                                                                                                	|
|                	|            	|                     	|                                                                                                                               	|
| appProperties  	| collection 	| has                 	| Private custom file properties.                                                                                               	|
|                	|            	|                     	|                                                                                                                               	|
| visibility     	| string     	| =, '!='             	| The visibility level of the file. Valid values are anyoneCanFind, anyoneWithLink, domainCanFind, domainWithLink, and limited. 	|

###6- Lớp GoogleDriveUtils

GoogleDriveUtils.java
```java
package org.o7planning.googledrive.utils;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
 
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
 
public class GoogleDriveUtils {
 
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
 
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
 
    // Directory to store user credentials for this application.
    private static final java.io.File CREDENTIALS_FOLDER //
            = new java.io.File(System.getProperty("user.home"), "credentials");
 
    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
 
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
 
    // Global instance of the {@link FileDataStoreFactory}.
    private static FileDataStoreFactory DATA_STORE_FACTORY;
 
    // Global instance of the HTTP transport.
    private static HttpTransport HTTP_TRANSPORT;
 
    private static Drive _driveService;
 
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(CREDENTIALS_FOLDER);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
 
    public static Credential getCredentials() throws IOException {
 
        java.io.File clientSecretFilePath = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
 
        if (!clientSecretFilePath.exists()) {
            throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME //
                    + " to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
        }
 
        InputStream in = new FileInputStream(clientSecretFilePath);
 
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
 
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
 
        return credential;
    }
 
    public static Drive getDriveService() throws IOException {
        if (_driveService != null) {
            return _driveService;
        }
        Credential credential = getCredentials();
        //
        _driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) //
                .setApplicationName(APPLICATION_NAME).build();
        return _driveService;
    }
 
}
```

###7- SubFolder & RootFolder

Nếu bạn biết ID của một thư mục (Directory) bạn có thể lấy được danh sách các thư mục con của thư mục này. Hãy chú ý trong Google Drive một tập tin (hoặc thư mục) có thể có một hoặc nhiều thư mục cha.
Bây giờ, hãy xem ví dụ lấy danh sách các thư mục con của một thư mục, và ví dụ lấy ra danh sách các thư mục gốc.

GetSubFolders.java

package org.o7planning.googledrive.example;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.o7planning.googledrive.utils.GoogleDriveUtils;
 
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
 
public class GetSubFolders {
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleSubFolders(String googleFolderIdParent) throws IOException {
 
        Drive driveService = GoogleDriveUtils.getDriveService();
 
        String pageToken = null;
        List<File> list = new ArrayList<File>();
 
        String query = null;
        if (googleFolderIdParent == null) {
            query = " mimeType = 'application/vnd.google-apps.folder' " //
                    + " and 'root' in parents";
        } else {
            query = " mimeType = 'application/vnd.google-apps.folder' " //
                    + " and '" + googleFolderIdParent + "' in parents";
        }
 
        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime
                    .setFields("nextPageToken, files(id, name, createdTime)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        //
        return list;
    }
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleRootFolders() throws IOException {
        return getGoogleSubFolders(null);
    }
 
    public static void main(String[] args) throws IOException {
 
        List<File> googleRootFolders = getGoogleRootFolders();
        for (File folder : googleRootFolders) {
 
            System.out.println("Folder ID: " + folder.getId() + " --- Name: " + folder.getName());
        }
    }
 
}

![](https://s1.o7planning.com/vi/11889/images/20687058.png)

**_Chú ý: Bạn có một đối tượng com.google.api.services.drive.model.File, nhưng không phải tất cả các trường (field) của nó đều được gán giá trị. Chỉ các trường bạn quan tâm mới được gán giá trị, ngược lại nó có giá trị null._**
```java
FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime
                    .setFields("nextPageToken, files(id, name, createdTime)")//
                    .setPageToken(pageToken).execute();

```

Một ví dụ tiếp theo, tìm kiếm các thư mục theo tên, là thư mục con của một thư mục nào đó.

GetSubFoldersByName.java
```java
package org.o7planning.googledrive.example;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.o7planning.googledrive.utils.GoogleDriveUtils;
 
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
 
public class GetSubFoldersByName {
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleSubFolderByName(String googleFolderIdParent, String subFolderName)
            throws IOException {
 
        Drive driveService = GoogleDriveUtils.getDriveService();
 
        String pageToken = null;
        List<File> list = new ArrayList<File>();
 
        String query = null;
        if (googleFolderIdParent == null) {
            query = " name = '" + subFolderName + "' " //
                    + " and mimeType = 'application/vnd.google-apps.folder' " //
                    + " and 'root' in parents";
        } else {
            query = " name = '" + subFolderName + "' " //
                    + " and mimeType = 'application/vnd.google-apps.folder' " //
                    + " and '" + googleFolderIdParent + "' in parents";
        }
 
        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    .setFields("nextPageToken, files(id, name, createdTime)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        //
        return list;
    }
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleRootFoldersByName(String subFolderName) throws IOException {
        return getGoogleSubFolderByName(null,subFolderName);
    }
 
    public static void main(String[] args) throws IOException {
 
        List<File> rootGoogleFolders = getGoogleRootFoldersByName("TEST");
        for (File folder : rootGoogleFolders) {
 
            System.out.println("Folder ID: " + folder.getId() + " --- Name: " + folder.getName());
        }
    }
     
}
```

###8- Search Files

Để tìm các File trên Google Drive, bạn nên sử dụng điều kiện truy vấn sau:
mimeType != 'application/vnd.google-apps.folder'

FindFilesByName.java
```java
package org.o7planning.googledrive.example;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.o7planning.googledrive.utils.GoogleDriveUtils;
 
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
 
public class FindFilesByName {
 
    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleFilesByName(String fileNameLike) throws IOException {
 
        Drive driveService = GoogleDriveUtils.getDriveService();
 
        String pageToken = null;
        List<File> list = new ArrayList<File>();
 
        String query = " name contains '" + fileNameLike + "' " //
                + " and mimeType != 'application/vnd.google-apps.folder' ";
 
        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime, mimeType
                    .setFields("nextPageToken, files(id, name, createdTime, mimeType)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        //
        return list;
    }
 
    public static void main(String[] args) throws IOException {
 
        List<File> rootGoogleFolders = getGoogleFilesByName("u");
        for (File folder : rootGoogleFolders) {
 
            System.out.println("Mime Type: " + folder.getMimeType() + " --- Name: " + folder.getName());
        }
 
        System.out.println("Done!");
    }
     
}
```
![](https://s1.o7planning.com/vi/11889/images/20698709.png)

###9- Tạo thư mục trên Google Drive

CreateFolder.java
```java
package org.o7planning.googledrive.example;
 
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
 
import org.o7planning.googledrive.utils.GoogleDriveUtils;
 
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
 
public class CreateFolder {
 
    public static final File createGoogleFolder(String folderIdParent, String folderName) throws IOException {
 
        File fileMetadata = new File();
 
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
 
        if (folderIdParent != null) {
            List<String> parents = Arrays.asList(folderIdParent);
 
            fileMetadata.setParents(parents);
        }
        Drive driveService = GoogleDriveUtils.getDriveService();
 
        // Create a Folder.
        // Returns File object with id & name fields will be assigned values
        File file = driveService.files().create(fileMetadata).setFields("id, name").execute();
 
        return file;
    }
 
    public static void main(String[] args) throws IOException {
 
        // Create a Root Folder
        File folder = createGoogleFolder(null, "TEST-FOLDER");
         
        System.out.println("Created folder with id= "+ folder.getId());
        System.out.println("                    name= "+ folder.getName());
 
        System.out.println("Done!");
    }
     
}
```
![](https://s1.o7planning.com/vi/11889/images/20700431.png)

###10- Tạo File trên Google Drive

Có 3 cách thông dụng để bạn tạo một File trên Google Driver:
- Upload File
- byte[]
- InputStream

CreateGoogleFile.java

```java
package org.o7planning.googledrive.example;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
 
import org.o7planning.googledrive.utils.GoogleDriveUtils;
 
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
 
public class CreateGoogleFile {
 
    // PRIVATE!
    private static File _createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {
 
        File fileMetadata = new File();
        fileMetadata.setName(customFileName);
 
        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        //
        Drive driveService = GoogleDriveUtils.getDriveService();
 
        File file = driveService.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();
 
        return file;
    }
 
    // Create Google File from byte[]
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, byte[] uploadData) throws IOException {
        //
        AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }
 
    // Create Google File from java.io.File
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, java.io.File uploadFile) throws IOException {
 
        //
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }
 
    // Create Google File from InputStream
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, InputStream inputStream) throws IOException {
 
        //
        AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }
 
    public static void main(String[] args) throws IOException {
 
        java.io.File uploadFile = new java.io.File("/home/tran/Downloads/test.txt");
 
        // Create Google File:
 
        File googleFile = createGoogleFile(null, "text/plain", "newfile.txt", uploadFile);
 
        System.out.println("Created Google file!");
        System.out.println("WebContentLink: " + googleFile.getWebContentLink() );
        System.out.println("WebViewLink: " + googleFile.getWebViewLink() );
 
        System.out.println("Done!");
    }
     
}
```
![](https://s1.o7planning.com/vi/11889/images/20701123.png)

###11- Chia sẻ Google File/Folder

ShareGoogleFile.java
```java
package org.o7planning.googledrive.example;
 
import java.io.IOException;
 
import org.o7planning.googledrive.utils.GoogleDriveUtils;
 
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
 
public class ShareGoogleFile {
 
    // Public a Google File/Folder.
    public static Permission createPublicPermission(String googleFileId) throws IOException {
        // All values: user - group - domain - anyone
        String permissionType = "anyone";
        // All values: organizer - owner - writer - commenter - reader
        String permissionRole = "reader";
 
        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);
 
        Drive driveService = GoogleDriveUtils.getDriveService();
        return driveService.permissions().create(googleFileId, newPermission).execute();
    }
 
    public static Permission createPermissionForEmail(String googleFileId, String googleEmail) throws IOException {
        // All values: user - group - domain - anyone
        String permissionType = "user"; // Valid: user, group
        // organizer - owner - writer - commenter - reader
        String permissionRole = "reader";
 
        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);
 
        newPermission.setEmailAddress(googleEmail);
 
        Drive driveService = GoogleDriveUtils.getDriveService();
        return driveService.permissions().create(googleFileId, newPermission).execute();
    }
 
    public static void main(String[] args) throws IOException {
 
        String googleFileId1 = "some-google-file-id-1";
        String googleEmail = "test.o7planning@gmail.com";
 
        // Share for a User
        createPermissionForEmail(googleFileId1, googleEmail);
 
        String googleFileId2 = "some-google-file-id-2";
 
        // Share for everyone
        createPublicPermission(googleFileId2);
 
        System.out.println("Done!");
    }
 
}
```



