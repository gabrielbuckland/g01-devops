<script>
	import NewsletterForm from '$lib/components/NewsletterForm.svelte';
</script>

<div
	class="relative flex min-h-screen items-center justify-center overflow-hidden
         bg-gradient-to-br from-indigo-100 via-white to-indigo-200
         dark:from-gray-900 dark:via-gray-950 dark:to-black"
>
	<div
		class="absolute top-[-10rem] left-[-10rem] h-[30rem] w-[30rem]
           rounded-full bg-indigo-300 opacity-30 blur-3xl
           dark:bg-indigo-700 dark:opacity-20"
	></div>
	<div
		class="absolute right-[-8rem] bottom-[-8rem] h-[25rem] w-[25rem]
           rounded-full bg-purple-300 opacity-30 blur-3xl
           dark:bg-purple-700 dark:opacity-20"
	></div>
	<div class="z-10 w-full max-w-md p-6">
		<NewsletterForm />
	</div>
</div>
