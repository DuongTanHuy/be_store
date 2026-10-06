Để hiểu Denpendence Injection thì trước tiên phải hiểu:

1. Spring bean: là các object được Spring Container (IoC Container -> Inverse of Control) tạo ra và quản lý hoàn toàn. Những class có anotaton @Component, @Service, @Repository, @Controller, @RestController được Spring IoC Container coi là Spring Bean.

2. Denpendence Injection: khi khai báo một object trong một class (không dùng new -> chỉ khai báo biến và đặt nó trong constructor có @Autowired), nếu object là một Spring bean [và nếu trong nó có các dependencies cũng là Spring Bean] -> thì Spring IoC Container sẽ tự động tiêm object [và cả các dependency vào object đó] -> DI.
   -> Lưu ý: chính class chứa constructor có @Autowired cũng phải là một Spring bean (DI xảy ra khi Spring Container tạo Spring Bean, và Spring Bean đó có phụ thuộc vào Spring Bean khác.)

3. IoC Container: là một "container" do Spring quản lý. Nó chứa tất cả các Spring Bean và quản lý cách chúng được tạo, cấu hình và kết nối với nhau. Nói cách khác, nó là "bộ não" điều khiển toàn bộ ứng dụng Spring.

4. Có 3 cách DI:
   - Constructor Injection: Dùng constructor có @Autowired để inject dependencies.
   - Setter Injection: Dùng setter method có @Autowired để inject dependencies.
   - Field Injection: Dùng field có @Autowired để inject dependencies.
