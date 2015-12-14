package de.waschnick.happy.stars.business.star.control;

import lombok.SneakyThrows;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class StarImages {

    private List<String> files;

//    public static void main(String[] args) {
//        Images images = new Images();
//
//        Map<String, Integer> occs = new HashMap<>();
//        for (String s : images.readImageFiles()) {
//            occs.put("public/img/" + s, 0);
//        }
//
//        SecureRandom random = new SecureRandom();
//        for (int i = 0; i < 10000; i++) {
//            // FILL OCS
//            String imageUrlForName = images.getImageUrlForName(new BigInteger(130, random).toString(32));
//            occs.put(imageUrlForName, occs.get(imageUrlForName) + 1);
//        }
//
//        // PRINT OCS
//        for (Map.Entry<String, Integer> entry : occs.entrySet()) {
//            // FIXME Remove System.out
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
//        }
//
//    }

    public String getImageUrlForName(String name) {
        int hashCodeOfName = name.hashCode();

        List<String> files = readImageFiles();
        int indexOfFile = Math.abs(hashCodeOfName) % files.size();

        return "img/" + files.get(indexOfFile);
    }


    @SneakyThrows
    private List<String> readImageFiles() {
        if (files == null) {
            InputStream resourceAsStream = StarImages.class.getClassLoader().getResourceAsStream("img/");
            files = IOUtils.readLines(resourceAsStream, Charsets.UTF_8);
        }
        return files;
    }


}
