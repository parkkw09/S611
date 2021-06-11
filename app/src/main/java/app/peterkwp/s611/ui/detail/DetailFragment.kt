package app.peterkwp.s611.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import app.peterkwp.s611.R
import app.peterkwp.s611.databinding.FragmentDetailBinding
import app.peterkwp.s611.di.module.view.ViewModelFactory
import app.peterkwp.s611.domain.model.DetailBook
import app.peterkwp.s611.ui.MainViewModel
import app.peterkwp.s611.util.Log
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MainViewModel
    private val args: DetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailBinding

    private fun launchUi(isbn: String) {
        Log.d(TAG, "launchUi()")
        viewModel.getDetailBook(isbn) { information ->
            binding.apply {
                val authorPublisher = "${information.authors} / ${information.publisher}"
                val languageIsbn = "${information.language} / ${information.isbn13} / ${information.isbn10}"
                val info = "${information.pages} / ${information.year} / ${information.rating} / ${information.price}"
                this.bookTitle.text = information.title
                this.bookSubtitle.text = information.subtitle
                this.bookAuthorPublisher.text = authorPublisher
                this.bookLanguageIsbn.text = languageIsbn
                this.bookInfo.text = info
                this.bookDescription.text = information.desc
                this.bookUrl.text = information.url
                this.bookPdfLink.text = information.pdf.freeBook
                this.detailImage.let {
                    it.scaleType = ImageView.ScaleType.FIT_CENTER
                    requestManager.load(information.image)
                        .apply(RequestOptions().error(R.drawable.book))
                        .into(it)
                }

                this.fab.setOnClickListener { view -> checkBookmark(information, view) }
            }
        }
    }

    private fun checkBookmark(information: DetailBook, view: View) {
        when (viewModel.checkBookmark(information)) {
            true -> {
                viewModel.deleteBookmark(information)
                Snackbar.make(view, getString(R.string.delete_bookmark), Snackbar.LENGTH_SHORT).show()
            }
            false -> {
                viewModel.addBookmark(information)
                Snackbar.make(view, getString(R.string.add_bookmark), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView()")
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")
        launchUi(args.isbn)
    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}