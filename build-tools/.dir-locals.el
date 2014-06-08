((c++-mode . ((eval . (progn
                        (em-append-include-dirs (append (list (expand-file-name "~/src/apl-android/src"))
                                                        (if (eq system-type 'darwin)
                                                            (list "/Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home/include"
                                                                  "/Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home/include/darwin")
                                                          (list "/usr/local/jdk1.7.0_45/include"
                                                                "/usr/local/jdk1.7.0_45/include/linux"))))
                        (when (eq system-type 'darwin)
                          (em-append-include-dirs (list "/usr/local/Cellar/gettext/0.18.3.2/include")))
                        (flycheck-mode 1)
                        (company-mode 1))))))
