package android.slc.command.ui.fragment.de;

import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressBinding;
import android.slc.command.vm.DisasterExpressVm;

public class MyImageFragment extends AppMvvmBaseFragment<CommandFragmentDisasterExpressBinding, DisasterExpressVm> {

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.command_fragment_my_image;
    }
}
