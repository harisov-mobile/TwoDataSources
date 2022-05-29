package ru.internetcloud.twodatasources.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException
import javax.inject.Inject
import ru.internetcloud.twodatasources.R
import ru.internetcloud.twodatasources.TwoDataSourcesApp
import ru.internetcloud.twodatasources.databinding.FragmentSettingsBinding
import ru.internetcloud.twodatasources.di.ViewModelFactory
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.presentation.edit_note.EditNoteViewModel

class SettingsFragment : Fragment() {

    // даггер:
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TwoDataSourcesApp).component
    }

    private lateinit var viewModel: SettingsViewModel

    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() = _binding ?: throw IllegalStateException("FragmentSettingsBinding is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.dataSourceType.observe(viewLifecycleOwner) { currentDataSource ->
            when (currentDataSource) {
                DataSourceType.ROOM_DATABASE1 -> binding.datasource1RadioButton.isChecked = true
                DataSourceType.ROOM_DATABASE2 -> binding.datasource2RadioButton.isChecked = true
            }
        }
    }

    private fun setupClickListeners() {
        binding.datasourceRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.datasource1_radio_button -> viewModel.saveDataSourceTypeToStorage(DataSourceType.ROOM_DATABASE1)
                R.id.datasource2_radio_button -> viewModel.saveDataSourceTypeToStorage(DataSourceType.ROOM_DATABASE2)
            }
        }
    }
}
