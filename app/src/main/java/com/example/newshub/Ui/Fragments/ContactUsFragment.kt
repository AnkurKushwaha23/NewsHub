package com.example.newshub.Ui.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newshub.R
import com.example.newshub.Ui.MainActivity
import com.example.newshub.databinding.FragmentContactUsBinding


class ContactUsFragment : Fragment() {
   lateinit var binding: FragmentContactUsBinding

   object Constants{
       const val email = "ankursenpai@gmail.com"
       const val mob_No = "+917048216866"
       const val X_LINK = "https://twitter.com/AnkurKushwaha23"
       const val LINKEDIN_LINK = "https://www.linkedin.com/in/ankur-kushwaha-818791248/"
       const val GITHUB_LINK = "https://github.com/AnkurKushwaha23/"
   }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         binding = FragmentContactUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setTabLayoutVisibility(false)
        (activity as? MainActivity)?.toggleSearchAndContact(true)
        binding.apply {
            btnReport.setOnClickListener {
                getMail("NewsHub: Report Bug")
            }

            btnMail.setOnClickListener {
                getMail("NewsHub: Suggestions")
            }

            imgWhatsapp.setOnClickListener {
                val sNumber = Constants.mob_No
                val uri = Uri.parse("https://api.whatsapp.com/send?phone=$sNumber")
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = uri
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                Toast.makeText(requireContext(),"Thanks for Contacting Us !!",Toast.LENGTH_SHORT).show()
            }

            imgLink.setOnClickListener {
                openUrl(Constants.LINKEDIN_LINK)
            }

            imgX.setOnClickListener {
                openUrl(Constants.X_LINK)
            }

            imgGithub.setOnClickListener {
                openUrl(Constants.GITHUB_LINK)
            }
        }
    }
    private fun openUrl(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(url)
        )
        startActivity(intent)
        Toast.makeText(requireContext(),"Thanks for Contacting Us !!",Toast.LENGTH_SHORT).show()
    }

    private fun getMail(subject : String){
        val uriBuilder = StringBuilder("mailto:" + Uri.encode(Constants.email))
        uriBuilder.append("?subject=" + Uri.encode(subject))
        val uriString = uriBuilder.toString()

        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(uriString))
        startActivity(Intent.createChooser(intent, "Send Suggestions"))
        Toast.makeText(requireContext(),"Thanks for Contacting Us !!",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? MainActivity)?.setTabLayoutVisibility(true)
        (activity as? MainActivity)?.toggleSearchAndContact(false)
    }
}