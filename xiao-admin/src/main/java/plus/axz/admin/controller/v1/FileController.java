package plus.axz.admin.controller.v1;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.admin.service.UserLoginService;
import plus.axz.model.admin.vo.FileVo;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.utils.alioss.AliOssUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xiaoxiang
 * description 文件上传下载
 */
@RestController
@RequestMapping("/files")
public class FileController extends LoginController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    public FileController(UserLoginService userLoginService) {
        super(userLoginService);
    }


    /**
     * 上传接口
     *
     * @param file 文件
     * @throws IOException IO异常
     */
    @PostMapping("/upload")
    public ResponseResult<?> upload(MultipartFile file) throws IOException {
        // 获取源文件的名称
        String originalFilename = file.getOriginalFilename();
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        // 获取上传的路径
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;
        File saveFile = new File(rootFilePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        // 把文件写入到上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        // 返回结果 url
        return ResponseResult.okResult("http://" + ip + ":" + port + "/files/" + flag);
    }

    /**
     * 富文本文件上传接口
     *
     * @param file 文件
     * @throws IOException IO异常
     */
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        // 获取源文件的名称
        String originalFilename = file.getOriginalFilename();
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        // 获取上传的路径
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;
        File saveFile = new File(rootFilePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        // 把文件写入到上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        String url = "http://" + ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        // 返回结果 url
        return json;
    }

    /**
     * 下载接口
     *
     * @param flag     文件标识
     * @param response 响应
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        // 新建一个输出流对象
        OutputStream os;
        // 定于文件上传的根路径
        String basePath = System.getProperty("user.dir") + "/files/";
        // 获取所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(basePath);
        // 找到跟参数一致的文件
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (CharSequenceUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                // 通过文件的路径读取文件字节流
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                // 通过输出流返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败");
        }
    }

    /**
     * OSS文件上传
     *
     * @param file 文件
     */
    @PostMapping("/upload/oss")
    public ResponseResult<?> ossUpload(MultipartFile file) {
        // 返回结果 url
        return ResponseResult.okResult(AliOssUtil.upload("test/", file));
    }

    /**
     * OSS文件删除
     *
     * @param fileVo 文件存储路径
     */
    @DeleteMapping("/delete/oss")
    public ResponseResult<?> ossUpload(@RequestBody FileVo fileVo) {
        AliOssUtil.delete(fileVo.getFilekey());
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
