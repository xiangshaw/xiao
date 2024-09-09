package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.article.mapper.AuthorMapper;
import plus.axz.article.service.AuthorService;
import plus.axz.model.article.pojos.Author;

/**
 * @author xiaoxiang
 * description
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {
}
