import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface LendService extends IService<TLendrecord> {
    Integer getStatus(Long user_id);
}