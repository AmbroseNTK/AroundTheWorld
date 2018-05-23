package ntk.ambrose.aroundtheworld.Models;

/**
 * Lưu thông tin một quốc gia
 */
public class Country {

    private String name;
    private String code;
    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public Country(){
        setName("");
        setCode("");
        setLanguage("");
    }

    /**
     * Tạo một quốc gia
     * @param name tên quốc gia
     * @param code mã quốc gia
     * @param language ngôn ngữ
     */
    public Country(String name,String code, String language) {
        setName(name);
        setCode(code);
        setLanguage(language);
    }

}
