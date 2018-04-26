require 'rake'
FileList = Rake::FileList

Pod::Spec.new do |s|

  s.name             = 'j2objclib'
    s.version          = '0.1.0'
    s.summary          = 'J2objc code framework'

    s.description      = <<-DESC
  TODO: Add long description of the pod here.
                         DESC

    s.homepage         = 'http://j2objc.org/'
    s.license          = { :type => 'Apache 2.0' }
    s.authors           = { 'Filler Person' => 'filler@example.com' }
    s.source           = { :git => 'https://github.com/google/j2objc.git'}

    s.ios.deployment_target = '8.0'

    s.source_files = FileList["build/j2objclib.h"].include("build/j2objcBuild/dependencies/out/main/**/*.{h,m,cpp,properites,txt}").include("build/j2objcBuild/source/out/main/**/*.{h,m,cpp,properites,txt}").include("build/j2objcBuild/dependencies/exploded/j2objc/co_doppl_lib_androidbase_0_9_0_0/src/**/*.{h,m,cpp,properites,txt}").to_ary

    s.public_header_files = FileList["build/j2objclib.h"].include("build/j2objcBuild/dependencies/out/main/**/*.h").include("build/j2objcBuild/source/out/main/**/*.h").exclude(/cpphelp/).exclude(/jni/).to_ary

    s.requires_arc = false
    s.libraries = 'z', 'sqlite3', 'iconv', 'jre_emul'
    s.frameworks = 'UIKit'

    s.pod_target_xcconfig = {
     'HEADER_SEARCH_PATHS' => '/Users/kgalligan/.j2objc/runtime/j2objc-2.1.1/include /Users/kgalligan/devel-kotlin/architecture/calculator/kurgan-ios/JustDbExtract/app/build/j2objcBuild/dependencies/exploded/j2objc/co_doppl_lib_androidbase_0_9_0_0/src','LIBRARY_SEARCH_PATHS' => '/Users/kgalligan/.j2objc/runtime/j2objc-2.1.1/lib',
     'OTHER_LDFLAGS' => '-ObjC',
'CLANG_WARN_DOCUMENTATION_COMMENTS' => 'NO',
'GCC_WARN_64_TO_32_BIT_CONVERSION' => 'NO'
    }
    
    s.user_target_xcconfig = {
     'HEADER_SEARCH_PATHS' => '/Users/kgalligan/.j2objc/runtime/j2objc-2.1.1/frameworks/JRE.framework/Headers'
    }
    
    
    
end